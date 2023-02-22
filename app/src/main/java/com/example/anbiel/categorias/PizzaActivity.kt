package com.example.anbiel.categorias

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anbiel.Modelo.Model
import com.example.anbiel.R
import com.example.anbiel.databinding.ActivityPizzaBinding
import com.example.anbiel.rv2.UserMyAdapter
import com.example.anbiel.rvTacos.TacosAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class PizzaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPizzaBinding
    private lateinit var auth: FirebaseAuth
    lateinit var mDatabase : DatabaseReference
    lateinit var rvTacos: RecyclerView
    lateinit var imageArray: ArrayList<Model>
    private lateinit var adapter: TacosAdapter
    private lateinit var imageMutableList:MutableList<Model>
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPizzaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        mDatabase = FirebaseDatabase.getInstance().getReference("Imagenes")

        rvTacos = findViewById(R.id.rvTacos)
        rvTacos.layoutManager = LinearLayoutManager(this)
        rvTacos.setHasFixedSize(true)
        imageArray = arrayListOf<Model>()

        getImageData()
        buscador()
        animationAll()
        binding.backCategory.setOnClickListener { finish() }
    }
    private fun animationAll() {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.textView.startAnimation(alphaAnimation)
        binding.textView20.startAnimation(alphaAnimation)
        binding.textView24.startAnimation(alphaAnimation)
        binding.buscadorCategoria.startAnimation(alphaAnimation)
        binding.rvTacos.startAnimation(alphaAnimation)

        val scale = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = 600

        binding.backCategory.startAnimation(scale)

    }
    override fun onResume() {
        super.onResume()
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.textView.startAnimation(alphaAnimation)
        binding.textView20.startAnimation(alphaAnimation)
        binding.textView24.startAnimation(alphaAnimation)
        binding.buscadorCategoria.startAnimation(alphaAnimation)
        binding.rvTacos.startAnimation(alphaAnimation)

        val scale = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = 600

        binding.backCategory.startAnimation(scale)

    }

    private fun getImageData() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Imagenes")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imageArray.clear()
                if (snapshot.exists()){
                    for (imageSnapshot in snapshot.children){
                        val image = imageSnapshot.getValue(Model::class.java)
                        if (image!!.category == "Pizza") {
                            imageArray.add(index = 0, image)
                        }
                    }
                    rvTacos.adapter = UserMyAdapter(imageArray, rvTacos)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun buscador() {
        val searchView = findViewById<SearchView>(R.id.buscadorCategoria)
        searchView.setQueryHint("Busca tu comida aqui...")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Imagenes")
        searchView.isIconifiedByDefault = false
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Obtener todos los datos en una lista
                val items = dataSnapshot.children.map { it.getValue(Model::class.java) }

                // Establecer un escuchador para el evento de búsqueda del SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        // Filtrar la lista de items según el texto de búsqueda
                        val filteredList = items.filter {
                            it?.name?.contains(newText, ignoreCase = true) == true && it.category == "Pizza"
                        }
                        // Actualizar la lista de items mostrados en la aplicación
                        updateList(filteredList as MutableList<Model>)
                        return true
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                // Mostrar un mensaje de error en caso de que ocurra un problema al obtener los datos de la base de datos
                Toast.makeText(this@PizzaActivity, "Error al obtener los datos de la base de datos: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun updateList(filteredList: List<Model>) {
        val adapter = UserMyAdapter(imageArray, rvTacos)
        rvTacos.adapter = adapter
        adapter.items = filteredList as MutableList<Model>
    }
}
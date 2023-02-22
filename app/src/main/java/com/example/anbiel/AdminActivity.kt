package com.example.anbiel

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anbiel.Modelo.Model
import com.example.anbiel.Profile.ProfileActivity
import com.example.anbiel.databinding.ActivityAdminBinding
import com.example.anbiel.rv.MyAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var auth: FirebaseAuth
    lateinit var mDatabase : DatabaseReference
    lateinit var rv: RecyclerView
    lateinit var imageArray: MutableList<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        mDatabase = FirebaseDatabase.getInstance().getReference("Imagenes")
        buscador()
        getImageData()
        checkUser()
        initRv()

        val button = findViewById<FloatingActionButton>(R.id.button)
        binding.btnBackEditEdit.setOnClickListener {
            alertdialog()
        }
        button.setOnClickListener {
            val intent = Intent(this, SelectorActivity::class.java)
            startActivity(intent)
        }
        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.searcnView.startAnimation(alphaAnimation)
        binding.titleTvUser.startAnimation(alphaAnimation)
        binding.subTitleTvUser.startAnimation(alphaAnimation)
        binding.textView19.startAnimation(alphaAnimation)
        binding.btnProfile.startAnimation(alphaAnimation)
        binding.btnBackEditEdit.startAnimation(alphaAnimation)
        binding.button.startAnimation(alphaAnimation)

    }

    override fun onResume() {
        super.onResume()
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.searcnView.startAnimation(alphaAnimation)
        binding.titleTvUser.startAnimation(alphaAnimation)
        binding.subTitleTvUser.startAnimation(alphaAnimation)
        binding.textView19.startAnimation(alphaAnimation)
        binding.btnProfile.startAnimation(alphaAnimation)
        binding.btnBackEditEdit.startAnimation(alphaAnimation)
        binding.button.startAnimation(alphaAnimation)
    }
    private fun initRv() {
        rv = findViewById(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)
        imageArray = arrayListOf<Model>()
    }
    private fun buscador() {
        val searchView = findViewById<SearchView>(R.id.searcnView)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Imagenes")
        searchView.isIconifiedByDefault = false
        searchView.setQueryHint("Busca tu comida aqui...")
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
                            it?.name?.contains(newText, ignoreCase = true) == true ||
                                    it?.desc?.contains(newText, ignoreCase = true) == true
                        }
                        // Actualizar la lista de items mostrados en la aplicación
                        updateList(filteredList as MutableList<Model>)
                        return true
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                // Mostrar un mensaje de error en caso de que ocurra un problema al obtener los datos de la base de datos
                Toast.makeText(this@AdminActivity, "Error al obtener los datos de la base de datos: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun updateList(filteredList: List<Model>) {
        val adapter = MyAdapter(imageArray, rv)
        rv.adapter = adapter
        adapter.items = filteredList as MutableList<Model>
    }
    private fun alertdialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar sesion")
        builder.setMessage("¿Estas seguro de cerrar sesion?")
        builder.setIcon(R.drawable.burger_large)
        builder.setPositiveButton("Si"){dialogInterface, which ->
            auth.signOut()
            finish()
            Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("Cancelar"){dialogInterface , which ->

        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun getImageData() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Imagenes")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    imageArray.clear()
                    for (imageSnapshot in snapshot.children){
                        val image = imageSnapshot.getValue(Model::class.java)
                        imageArray.add(index = 0, image!!)
                    }
                    rv.adapter = MyAdapter(imageArray, rv)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null){
            binding.subTitleTvUser.text = "Unete"
        }else{
            val email = firebaseUser.email
            binding.subTitleTvUser.text = email
        }
    }
}
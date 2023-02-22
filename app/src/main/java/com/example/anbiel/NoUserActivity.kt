package com.example.anbiel

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anbiel.Modelo.Model
import com.example.anbiel.Register.LoginActivity
import com.example.anbiel.categorias.*
import com.example.anbiel.databinding.ActivityNoUserBinding

import com.example.anbiel.rv2.UserMyAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class NoUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoUserBinding
    private lateinit var auth: FirebaseAuth
    lateinit var mDatabase : DatabaseReference
    lateinit var rv: RecyclerView
    lateinit var imageArray: ArrayList<Model>

    private lateinit var adapter: UserMyAdapter
    private lateinit var imageMutableList:MutableList<Model>
    private lateinit var progressDialog: ProgressDialog

    private var userList: MutableList<Model> = mutableListOf()
    private var userListLikes: MutableList<Model> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere un momento")
        progressDialog.setCanceledOnTouchOutside(false)

        auth = Firebase.auth
        mDatabase = FirebaseDatabase.getInstance().getReference("Imagenes")

        rv = findViewById(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)
        imageArray = arrayListOf<Model>()

        getImageData()
        checkUser()
        buscador()
        animationAll()
        clickPhotoCategory()
        clickTextCategory()

    }
    private fun alertdialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("  ERRO  ")
        builder.setMessage("Este producto no existe...")
        builder.setIcon(R.drawable.burger_large)
        builder.setPositiveButton("OK"){dialogInterface, which ->
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    
    private fun clickTextCategory() {
        binding.tv1.setOnClickListener {
            val intent = Intent(this, TacosActivity::class.java)
            startActivity(intent)
        }
        binding.tv2.setOnClickListener {
            startActivity(Intent(this, HamburgerActivity::class.java))
        }
        binding.tv3.setOnClickListener {
            startActivity(Intent(this, PizzaActivity ::class.java))
        }
        binding.tv4.setOnClickListener {
            startActivity(Intent(this, BebidasActivity ::class.java))
        }
        binding.tv5.setOnClickListener {
            startActivity(Intent(this, PostresActivity ::class.java))
        }
        binding.tv6.setOnClickListener {
            startActivity(Intent(this, ShushiActivity ::class.java))
        }
        binding.tv7.setOnClickListener {
            startActivity(Intent(this, PolloActivity ::class.java))
        }
        binding.tv8.setOnClickListener {
            startActivity(Intent(this, SaladActivity ::class.java))
        }
    }
    private fun clickPhotoCategory() {
        binding.imgPersonProfileUser.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.verTodoCategorias.setOnClickListener {
            startActivity(Intent(this, CategoryDetailActivity::class.java))
        }

        binding.ivCTacos.setOnClickListener {
            val intent = Intent(this, TacosActivity::class.java)
            startActivity(intent)
        }
        binding.ivCHamburger.setOnClickListener {
            startActivity(Intent(this, HamburgerActivity::class.java))
        }
        binding.ivCPizza.setOnClickListener {
            startActivity(Intent(this, PizzaActivity ::class.java))
        }
        binding.ivCBebidas.setOnClickListener {
            startActivity(Intent(this, BebidasActivity ::class.java))
        }
        binding.ivCPostres.setOnClickListener {
            startActivity(Intent(this, PostresActivity ::class.java))
        }
        binding.ivCShushi.setOnClickListener {
            startActivity(Intent(this, ShushiActivity ::class.java))
        }
        binding.ivCPollo.setOnClickListener {
            startActivity(Intent(this, PolloActivity ::class.java))
        }
        binding.ivCSalad.setOnClickListener {
            startActivity(Intent(this, SaladActivity ::class.java))
        }
    }
    private fun animationAll() {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.titleTvUser.startAnimation(alphaAnimation)
        binding.textView25.startAnimation(alphaAnimation)
        binding.searchViewHome.startAnimation(alphaAnimation)
        binding.textView16.startAnimation(alphaAnimation)
        binding.textView18.startAnimation(alphaAnimation)
        binding.subTitleTvUser.startAnimation(alphaAnimation)
        binding.verTodoCategorias.startAnimation(alphaAnimation)
        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 600
        animation.fillAfter = true
        binding.imgPersonProfileUser.startAnimation(animation)

        binding.tv1.startAnimation(alphaAnimation)
        binding.tv2.startAnimation(alphaAnimation)
        binding.tv3.startAnimation(alphaAnimation)
        binding.tv4.startAnimation(alphaAnimation)
        binding.tv5.startAnimation(alphaAnimation)
        binding.tv6.startAnimation(alphaAnimation)
        binding.tv7.startAnimation(alphaAnimation)
        binding.tv8.startAnimation(alphaAnimation)

        val scale = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = 600

        binding.card1.startAnimation(scale)
        binding.card2.startAnimation(scale)
        binding.card3.startAnimation(scale)
        binding.card4.startAnimation(scale)
        binding.card5.startAnimation(scale)
        binding.card6.startAnimation(scale)
        binding.card7.startAnimation(scale)
        binding.card8.startAnimation(scale)

    }
    override fun onResume() {
        super.onResume()
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.titleTvUser.startAnimation(alphaAnimation)
        binding.textView25.startAnimation(alphaAnimation)
        binding.searchViewHome.startAnimation(alphaAnimation)
        binding.textView16.startAnimation(alphaAnimation)
        binding.textView18.startAnimation(alphaAnimation)
        binding.subTitleTvUser.startAnimation(alphaAnimation)
        binding.verTodoCategorias.startAnimation(alphaAnimation)
        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 600
        animation.fillAfter = true
        binding.imgPersonProfileUser.startAnimation(animation)
        binding.tv1.startAnimation(alphaAnimation)
        binding.tv2.startAnimation(alphaAnimation)
        binding.tv3.startAnimation(alphaAnimation)
        binding.tv4.startAnimation(alphaAnimation)
        binding.tv5.startAnimation(alphaAnimation)
        binding.tv6.startAnimation(alphaAnimation)
        binding.tv7.startAnimation(alphaAnimation)
        binding.tv8.startAnimation(alphaAnimation)

        val scale = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = 600

        binding.card1.startAnimation(scale)
        binding.card2.startAnimation(scale)
        binding.card3.startAnimation(scale)
        binding.card4.startAnimation(scale)
        binding.card5.startAnimation(scale)
        binding.card6.startAnimation(scale)
        binding.card7.startAnimation(scale)
        binding.card8.startAnimation(scale)

    }
    private fun buscador() {
        val searchView = findViewById<SearchView>(R.id.searchViewHome)
        searchView.setQueryHint("Busca tu comida aqui.")
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
                            it?.name?.contains(newText, ignoreCase = true) == true
                        }
                        // Actualizar la lista de items mostrados en la aplicación
                        updateList(filteredList as MutableList<Model>)
                        return true
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                alertdialog()
                Toast.makeText(this@NoUserActivity, "Error al obtener los datos de la base de datos: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun updateList(filteredList: List<Model>) {
        val adapter = UserMyAdapter(imageArray, rv)
        rv.adapter = adapter
        adapter.items = filteredList as MutableList<Model>
    }
    private fun getImageData() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Imagenes")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imageArray.clear()
                if (snapshot.exists()){
                    for (imageSnapshot in snapshot.children){
                        val image = imageSnapshot.getValue(Model::class.java)
                        imageArray.add(index = 0, image!!)
                    }
                    rv.adapter = UserMyAdapter(imageArray, rv)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null){
            binding.subTitleTvUser.text = "Únete"
        }
    }

}
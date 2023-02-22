package com.example.anbiel

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.anbiel.databinding.ActivityEditProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebase: FirebaseDatabase
    private var imageUriP: Uri? = null
    private var proID = ""
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere un momento")
        progressDialog.setCanceledOnTouchOutside(false)
        firebaseAuth =FirebaseAuth.getInstance()

        val name = findViewById<TextView>(R.id.Edit_Name_Producto)
        val desc = findViewById<TextView>(R.id.desc_Edit_Producto)
        val image = findViewById<ImageView>(R.id.Edit_image_Producto)
        val precio = findViewById<TextView>(R.id.desc2_Edit_Producto)

        val intent = intent
        val nameDetail = intent.getStringExtra("name")
        val descDetail = intent.getStringExtra("desc")
        val imageDetail = intent.getStringExtra("image")
        val precioDetail = intent.getStringExtra("precio")


        name.text = nameDetail
        desc.text = descDetail
        precio.text = precioDetail
        Picasso.get().load(getIntent().getStringExtra("image")).into(image)

        binding.btnEditProducto.setOnClickListener {
            subirActualizacion()
            startActivity(Intent(this, AdminActivity::class.java))
        }
        binding.EditImageProducto.setOnClickListener {

        }
        binding.btnBackEditEdit.setOnClickListener {
            finish()
        }

    }

    private fun subirActualizacion() {

    }

}
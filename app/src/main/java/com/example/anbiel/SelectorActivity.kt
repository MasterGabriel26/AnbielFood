package com.example.anbiel

import android.Manifest
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.PermissionGrantedResponse
import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.anbiel.Modelo.Model
import com.example.anbiel.databinding.ActivitySelectorBinding
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.PermissionToken
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.firebase.database.DatabaseReference
import com.karumi.dexter.listener.PermissionRequest

class SelectorActivity : AppCompatActivity() {

    var binding: ActivitySelectorBinding? = null
    var uri: Uri? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var firebaseStorage: FirebaseStorage? = null
    var firebaseFirestore: FirebaseFirestore? = null

    private var count = 0
    private var mInterstitialAd: InterstitialAd? = null

    private lateinit var categorySpinner: Spinner
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectorBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        categorySpinner = findViewById(R.id.spinner)
        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase!!.getReference("products")
        val categories = arrayOf("Tacos", "Hamburger", "Pizza", "Bebidas", "Ensalada","Shushi","Pollo", "Postres")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        binding!!.ivSelector.setOnClickListener {
            uploadImage()

        }
        binding!!.btnSelector.setOnClickListener {
            uploadImage()
        }
        binding!!.btnSelectorRegresar.setOnClickListener {
            finish()
        }
        binding!!.backSelector.setOnClickListener {
            finish()
        }
        binding!!.btnSelectorSubir.setOnClickListener {
            alertdialog()
            subirimage()

        }

        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding!!.textView11.startAnimation(alphaAnimation)
        binding!!.textView12.startAnimation(alphaAnimation)
        binding!!.textView13.startAnimation(alphaAnimation)
        binding!!.tv13.startAnimation(alphaAnimation)
        binding!!.etTitleImage.startAnimation(alphaAnimation)
        binding!!.etDescImage.startAnimation(alphaAnimation)
        binding!!.precioSelector.startAnimation(alphaAnimation)
        binding!!.spinner.startAnimation(alphaAnimation)
        binding!!.textView14.startAnimation(alphaAnimation)
        binding!!.imageView6.startAnimation(alphaAnimation)
        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 1000
        animation.fillAfter = true
        binding!!.backSelector.startAnimation(animation)
    }

    override fun onResume() {
        super.onResume()
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding!!.textView11.startAnimation(alphaAnimation)
        binding!!.textView12.startAnimation(alphaAnimation)
        binding!!.textView13.startAnimation(alphaAnimation)
        binding!!.tv13.startAnimation(alphaAnimation)
        binding!!.etTitleImage.startAnimation(alphaAnimation)
        binding!!.etDescImage.startAnimation(alphaAnimation)
        binding!!.precioSelector.startAnimation(alphaAnimation)
        binding!!.spinner.startAnimation(alphaAnimation)
        binding!!.textView14.startAnimation(alphaAnimation)
        binding!!.imageView6.startAnimation(alphaAnimation)
        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 700
        animation.fillAfter = true
        binding!!.backSelector.startAnimation(animation)
    }
    data class Product(
        val category: String
    )
    private fun subirimage() {
        val selectedCategory = categorySpinner.selectedItem.toString()
        val reference = firebaseStorage!!.reference.child("Images").child(System.currentTimeMillis().toString() + "")
        reference.putFile(uri!!).addOnSuccessListener {
            reference.downloadUrl.addOnSuccessListener { uri ->
                val model = Model()
                model.image = uri.toString()
                model.name = binding!!.etTitleImage.text.toString().trim { it <= ' ' }
                model.desc = binding!!.etDescImage.text.toString().trim { it <= ' ' }
                model.precio = binding!!.precioSelector.text.toString().trim{ it <= ' ' }
                model.category = selectedCategory

                val newProductRef = firebaseDatabase!!.reference.child("Imagenes").push()
                model.id = newProductRef.key
                newProductRef.setValue(model)

                finish()
            }
        }
    }
    private fun alertdialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Subiendo Image")
        builder.setMessage("Espere un momento por favor...")
        builder.setIcon(R.drawable.logo_noti)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun uploadImage() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, 101)
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                    Toast.makeText(this@SelectorActivity, "Permiso Denegado", Toast.LENGTH_SHORT).show()
                }
                override fun onPermissionRationaleShouldBeShown(permissionRequest: PermissionRequest, permissionToken: PermissionToken) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            uri = data!!.data
            binding!!.ivSelector.setImageURI(uri)
        }
    }
}
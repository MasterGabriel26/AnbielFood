package com.example.anbiel

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.anbiel.Modelo.Model
import com.example.anbiel.databinding.ActivityDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animationAll()

        val name = findViewById<TextView>(R.id.nameDetail)
        val desc = findViewById<TextView>(R.id.descDetail)
        val image = findViewById<ImageView>(R.id.imageDetail)
        val precio = findViewById<TextView>(R.id.tv_Precio)
        val correo = findViewById<TextView>(R.id.tvCorreo)

        binding.backDetail.setOnClickListener {
            finish()
        }

        val intent = intent
        val nameDetail = intent.getStringExtra("name")
        val descDetail = intent.getStringExtra("desc")
        val imageDetail = intent.getStringExtra("image")
        val precioDetail = intent.getStringExtra("precio")
        val correoDetail = intent.getStringExtra("correo")

        name.text = nameDetail
        desc.text = descDetail
        precio.text = precioDetail
        correo.text = correoDetail
        Picasso.get().load(getIntent().getStringExtra("image")).into(image)

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, UbicationActivity::class.java))
        }
        binding.addCart.setOnClickListener {

        }

    }

    private fun animationAll() {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.nameDetail.startAnimation(alphaAnimation)
        binding.textView2.startAnimation(alphaAnimation)
        binding.descDetail.startAnimation(alphaAnimation)
        binding.tvPrecio.startAnimation(alphaAnimation)
        binding.tvCorreo.startAnimation(alphaAnimation)
        binding.iconPrecio.startAnimation(alphaAnimation)
        binding.tvGps.startAnimation(alphaAnimation)

        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 600
        animation.fillAfter = true

        val scale = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = 600

        binding.floatingActionButton.startAnimation(scale)
        binding.backDetail.startAnimation(scale)
    }

    override fun onResume() {
        super.onResume()
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.nameDetail.startAnimation(alphaAnimation)
        binding.textView2.startAnimation(alphaAnimation)
        binding.descDetail.startAnimation(alphaAnimation)
        binding.tvPrecio.startAnimation(alphaAnimation)
        binding.tvCorreo.startAnimation(alphaAnimation)
        binding.iconPrecio.startAnimation(alphaAnimation)
        binding.tvGps.startAnimation(alphaAnimation)

        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 600
        animation.fillAfter = true

        val scale = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = 600

        binding.floatingActionButton.startAnimation(scale)
        binding.backDetail.startAnimation(scale)
    }
}

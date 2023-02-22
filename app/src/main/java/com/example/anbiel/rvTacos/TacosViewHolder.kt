package com.example.anbiel.rvTacos

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anbiel.DetailActivity
import com.example.anbiel.Modelo.Model
import com.example.anbiel.databinding.RowFavoritesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class TacosViewHolder(view: View, val rv: RecyclerView): RecyclerView.ViewHolder(view) {

    val binding = RowFavoritesBinding.bind(view)
    private lateinit var firebaseAuth: FirebaseAuth
    private var imageId = ""
    private var databaseReference = FirebaseDatabase.getInstance().reference
    fun render(model: Model) {
        binding.name.text = model.name
        binding.precioDetail.text = model.precio
        Glide.with(binding.ivCategory.context).load(model.image).into(binding.ivCategory)

        firebaseAuth = FirebaseAuth.getInstance()
        imageId = model.name!!

        binding.ivCategory.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("name", model.name)
            intent.putExtra("desc", model.desc)
            intent.putExtra("image", model.image)
            intent.putExtra("precio", model.precio)
            intent.putExtra("correo", model.correo)
            Toast.makeText(context, "${model.name}", Toast.LENGTH_SHORT).show()
            context.startActivity(intent)
        }
    }
}

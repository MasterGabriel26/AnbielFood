package com.example.anbiel.rv


import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anbiel.*
import com.example.anbiel.Modelo.Model
import com.example.anbiel.R
import com.example.anbiel.databinding.ItemSuperheroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ImageViewHolder(view:View, val rv: RecyclerView):RecyclerView.ViewHolder(view) {

    val binding = ItemSuperheroBinding.bind(view)

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private var imageId = ""
    private var isInMyFavorite = false



    fun render(model: Model) {
        binding.name.text = model.name
        binding.desc.text = model.desc
        binding.precioDetail.text = model.precio
        Glide.with(binding.ivCategory.context).load(model.image).into(binding.ivCategory)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.ivCategory.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("name", model.name)
            intent.putExtra("desc", model.desc)
            intent.putExtra("image", model.image)
            intent.putExtra("precio", model.precio)
            intent.putExtra("correo", model.correo)
            context.startActivity(intent)
        }
        binding.card.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("name", model.name)
            intent.putExtra("desc", model.desc)
            intent.putExtra("image", model.image)
            intent.putExtra("precio", model.precio)
            intent.putExtra("correo", model.correo)
            context.startActivity(intent)
        }
        binding.deleteItemAdmin.setOnClickListener {
            alertdialog(model)
        }
    }
    private fun alertdialog(model: Model) {
        val context = itemView.context
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Eliminar Producto")
        builder.setMessage("¿Estas seguro de Eliminar ${model.name} ?")
        builder.setIcon(R.drawable.burger_large)
        builder.setPositiveButton("Si"){dialogInterface, which ->
            // Obtener la posición del elemento en la lista del adaptador
            val position = adapterPosition
            // Eliminar el elemento del adaptador y de la base de datos
            (rv.adapter as MyAdapter).removeItem(position)
            Toast.makeText(context, "Se a eliminado ${model.name}", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("No"){dialogInterface , which ->

        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}



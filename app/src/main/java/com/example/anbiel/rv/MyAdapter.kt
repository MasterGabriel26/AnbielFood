package com.example.anbiel.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anbiel.Modelo.Model
import com.example.anbiel.R
import com.google.firebase.database.FirebaseDatabase

class MyAdapter(
    private var userList: MutableList<Model>,
    private val rv: RecyclerView
): RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return ImageViewHolder(layoutInflater, rv)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = userList[position]
        holder.render(item)

    }
    override fun getItemCount(): Int = userList.size

    private var fullList: MutableList<Model> = mutableListOf()
    var items: MutableList<Model>
        get() = userList
        set(value) {
            userList = value
            notifyDataSetChanged()
        }
    init {
        fullList.addAll(userList)
    }

    fun removeItem(position: Int) {
        // Obtener el ID del elemento a eliminar
        val itemId = userList[position].id

        // Obtener una referencia a la base de datos y eliminar el elemento con el ID especificado
        if (itemId != null) {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Imagenes").child(itemId)
            myRef.removeValue()
        }

        // Eliminar el elemento de la lista del adaptador
        userList.removeAt(position)
        notifyItemRemoved(position)
    }

}
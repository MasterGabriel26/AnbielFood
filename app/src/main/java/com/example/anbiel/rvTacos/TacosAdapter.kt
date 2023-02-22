package com.example.anbiel.rvTacos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anbiel.Modelo.Model
import com.example.anbiel.R

class TacosAdapter(
    private var tacosList: MutableList<Model>,
    private val rvTacos: RecyclerView
): RecyclerView.Adapter<TacosViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TacosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.row_favorites, parent, false)
        return TacosViewHolder(layoutInflater, rvTacos)
    }

    override fun onBindViewHolder(holder: TacosViewHolder, position: Int) {
        val item = tacosList[position]
        holder.render(item)

    }
    override fun getItemCount(): Int = tacosList.size

    private var fullList: MutableList<Model> = mutableListOf()
    var items: MutableList<Model>
        get() = tacosList
        set(value) {
            tacosList = value
            for (i in 0 until tacosList.size) {
                notifyItemChanged(i)
            }
        }
    init {
        fullList.addAll(tacosList)
    }

}

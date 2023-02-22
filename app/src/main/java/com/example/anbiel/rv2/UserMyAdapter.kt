package com.example.anbiel.rv2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anbiel.Modelo.CarritoItem
import com.example.anbiel.Modelo.Model
import com.example.anbiel.R

class UserMyAdapter(
    private var userList: MutableList<Model>,
    private val rv: RecyclerView
): RecyclerView.Adapter<UserImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.row_favorites, parent, false)
        return UserImageViewHolder(layoutInflater, rv)
    }

    override fun onBindViewHolder(holder: UserImageViewHolder, position: Int) {
        val item = userList[position]
        holder.render(item)

    }
    override fun getItemCount(): Int = userList.size

    private var fullList: MutableList<Model> = mutableListOf()
    var items: MutableList<Model>
        get() = userList
        set(value) {
            userList = value
            for (i in 0 until userList.size) {
                notifyItemChanged(i)
            }
        }
    init {
        fullList.addAll(userList)
    }

}

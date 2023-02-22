package com.example.anbiel.Modelo

import java.util.*

class Model {
    var category: String? = null
    var id: String? = null
    var name: String? = null
    var desc: String? = null
    var image: String? = null
    var precio: String? = null
    var correo: String? = null

    var likes: Long = 0
    var liked: Boolean = false
    var increment: Int = 0

    var date: Date? = null

    constructor() {}
    constructor(name: String?, desc: String?, image: String?, precio: String?, correo:String?,
                likes: Long, date: Date?, id: String?, liked: Boolean = false, increment: Int = 0, category: String?) {
        this.name = name
        this.desc = desc
        this.image = image
        this.correo = correo
        this.precio = precio
        this.likes = likes
        this.date = date
        this.id = id
        this.liked = liked
        this.increment = increment
        this.category = category
    }
}
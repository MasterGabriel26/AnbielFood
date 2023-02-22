package com.example.anbiel.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.anbiel.Modelo.Model
import com.example.anbiel.R
import com.example.anbiel.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var imageArrayList: ArrayList<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editProfile.setOnClickListener {
            startActivity(Intent(this, ProfileEditActivity::class.java))
        }
        binding.btnBackProfile.setOnClickListener {
            finish()
        }

        firebaseAuth =FirebaseAuth.getInstance()
        loadUser()
        cargarFavoritos()
    }
    private fun cargarFavoritos() {
        imageArrayList = ArrayList();
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!).child("Likes")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    imageArrayList.clear()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
    private fun loadUser() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val email = "${snapshot.child("email").value}"
                val name = "${snapshot.child("name").value}"
                val profileImage = "${snapshot.child("profileImage").value}"
                val postImage = "${snapshot.child("postImage").value}"
                val timestamp = "${snapshot.child("timestamp").value}"
                val desc = "${snapshot.child("desc").value}"
                val uid = "${snapshot.child("uid").value}"
                val userType = "${snapshot.child("userType").value}"

                binding.nameProfile.text = name
                binding.emailProfile.text = email
                binding.userProfile.text = userType
                binding.descProfile.text = desc

                try{
                    Glide.with(this@ProfileActivity).load(profileImage).placeholder(R.drawable.logo_noti).into(binding.profileProfile)
                }catch (e: Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
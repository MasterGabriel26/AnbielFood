package com.example.anbiel.Profile

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.anbiel.R
import com.example.anbiel.databinding.ActivityProfileEditBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var imageUri: Uri? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere un momento")
        progressDialog.setCanceledOnTouchOutside(false)
        firebaseAuth =FirebaseAuth.getInstance()
        loadUserInfo()

        binding.btnEditProfile.setOnClickListener {
            validateData()
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.EditImageProfile.setOnClickListener {
            showImage()
        }
        binding.btnBackEditEdit.setOnClickListener {
            finish()
        }

    }

    private var name = ""
    private var desc = ""
    private fun validateData() {
        name = binding.EditNameProfile.text.toString().trim()
        desc = binding.descEditProfile.text.toString().trim()
        if (name.isEmpty()){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }else{
            if (imageUri == null){
                updateProfile("")
            }
            else{
                updateImage()
            }
        }
    }
    private fun updateImage() {
        progressDialog.setMessage("Subiendo imagen de perfil")
        progressDialog.show()

        val filePathAndName = "ProfileImages/"+firebaseAuth.uid
        val reference = FirebaseStorage.getInstance().getReference(filePathAndName)
        reference.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapShot ->
                val uriTask: Task<Uri> =taskSnapShot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadImageUrl = "${uriTask.result}"
                updateProfile(uploadImageUrl)

            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show()
            }


    }
    private fun updateProfile(uploadImageUrl: String) {
        progressDialog.setMessage("Actualizando Perfil")
        val hashMap: HashMap<String,Any> = HashMap()
        hashMap["name"] = "$name"
        hashMap["desc"] = "$desc"

        if (imageUri != null){
            hashMap["profileImage"] = uploadImageUrl
        }
        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(firebaseAuth.uid!!).updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show()
            }
    }
    private fun showImage() {
        val popuMenu = PopupMenu(this, binding.EditImageProfile)
        popuMenu.menu.add(Menu.NONE,0,0, "Camera")
        popuMenu.menu.add(Menu.NONE,1,1, "Gallery")
        popuMenu.show()

        popuMenu.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id==0){
                pickImageCamera()
            }else if(id==1){
                pickImageGallery()
            }
            true
        }
    }
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }
    private fun pickImageCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)
    }
    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> {result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data
                //imageUri = data!!.data
                binding.EditImageProfile.setImageURI(imageUri)
            }else{
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            }
        }
    )
    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data
                imageUri = data!!.data
                binding.EditImageProfile.setImageURI(imageUri)

            }else{
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            }

        }
    )
    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = "${snapshot.child("name").value}"
                val profileImage = "${snapshot.child("profileImage").value}"
                val userType = "${snapshot.child("userType").value}"
                val desc = "${snapshot.child("desc").value}"

                binding.EditNameProfile.setText(name)
                binding.descEditProfile.setText(desc)
                try{
                    Glide.with(this@ProfileEditActivity).load(profileImage).placeholder(R.drawable.logo_noti).into(binding.EditImageProfile)
                }catch (e: Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
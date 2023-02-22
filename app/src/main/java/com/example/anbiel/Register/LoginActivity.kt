package com.example.anbiel.Register

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.Toast
import com.example.anbiel.AdminActivity
import com.example.anbiel.HomeActivity
import com.example.anbiel.R
import com.example.anbiel.databinding.ActivityLoginBinding
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    //notfication
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channlId = "com.demo.notification"
    private val description = "Test notification"
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        firebaseAuth = FirebaseAuth.getInstance()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.signInAppCompatButton.setOnClickListener {
            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()

            when {
                mPassword.isEmpty() || mEmail.isEmpty() -> {
                    Toast.makeText(this, "Email o contraseña o incorrectos.",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    signIn(mEmail, mPassword)
                }
            }
        }
        binding.signUpTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            this.startActivity(intent)
        }
        binding.backLogin.setOnClickListener {
            finish()
        }
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.textView3.startAnimation(alphaAnimation)
        binding.textView4.startAnimation(alphaAnimation)
        binding.textView5.startAnimation(alphaAnimation)
        binding.emailEditText.startAnimation(alphaAnimation)
        binding.passwordEditText.startAnimation(alphaAnimation)
        binding.signInAppCompatButton.startAnimation(alphaAnimation)
        binding.signUpTextView.startAnimation(alphaAnimation)
        binding.backLogin.startAnimation(alphaAnimation)

        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 1000
        animation.fillAfter = true
        binding.imageView2.startAnimation(animation)

    }
    override fun onResume() {
        super.onResume()
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.textView3.startAnimation(alphaAnimation)
        binding.textView4.startAnimation(alphaAnimation)
        binding.textView5.startAnimation(alphaAnimation)
        binding.emailEditText.startAnimation(alphaAnimation)
        binding.passwordEditText.startAnimation(alphaAnimation)
        binding.signInAppCompatButton.startAnimation(alphaAnimation)
        binding.signUpTextView.startAnimation(alphaAnimation)
        binding.backLogin.startAnimation(alphaAnimation)

        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 1000
        animation.fillAfter = true
        binding.imageView2.startAnimation(animation)

    }
    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    selectUserInfo()
                    reload()
                } else{
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Email o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun selectUserInfo() {
        val firebaseUser = firebaseAuth.currentUser!!
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseUser.uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userType = snapshot.child("userType").value
                if (userType == "user"){
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                }else if (userType == "admin"){7
                    val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun reload() {
        val pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channlId,description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channlId)
                .setContentTitle("Bienvenido")
                .setContentText("Logeo competado...")
                .setSmallIcon(R.drawable.burger_large)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.logo))
                .setContentIntent(pendingIntent)
        }else{
            builder = Notification.Builder(this)
                .setContentTitle("Bienvenido")
                .setContentText("Logeo competado...")
                .setSmallIcon(R.drawable.burger_large)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.logo))
                .setContentIntent(pendingIntent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            notificationManager.notify(1234,builder.build())
        }
    }
}
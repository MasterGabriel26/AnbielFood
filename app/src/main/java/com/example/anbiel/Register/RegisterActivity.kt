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
import android.util.Patterns
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import com.example.anbiel.R
import com.example.anbiel.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference

    //notfication
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channlId = "com.demo.notification"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        database = Firebase.database.reference

        binding.btnRegistrar.setOnClickListener {
            createUser()
        }
        binding.back.setOnClickListener {
            binding.back.setColorFilter(Color.BLACK)
            finish()
        }

       metodoAnimacion()
    }

    private fun metodoAnimacion() {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.textView6.startAnimation(alphaAnimation)
        binding.textView7.startAnimation(alphaAnimation)
        binding.textView8.startAnimation(alphaAnimation)
        binding.textView10.startAnimation(alphaAnimation)
        binding.textView9.startAnimation(alphaAnimation)
        binding.btnRegistrar.startAnimation(alphaAnimation)
        binding.etEmailRegister.startAnimation(alphaAnimation)
        binding.etNameRegister.startAnimation(alphaAnimation)
        binding.etPasswordRegister.startAnimation(alphaAnimation)
        binding.etConfirmPasswordRegister.startAnimation(alphaAnimation)
        binding.back.startAnimation(alphaAnimation)

        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 1000
        animation.fillAfter = true
        binding.imageView3.startAnimation(animation)
    }

    private fun createUser() {
        val mEmail = binding.etEmailRegister.text.toString()
        val mPassword = binding.etPasswordRegister.text.toString()
        val mRepeatPassword = binding.etConfirmPasswordRegister.text.toString()
        val mNameRegister = binding.etNameRegister.text.toString()

        val passwordRegex = Pattern.compile("^" +
                "(?=.*[-@#$%^&+=.,|!?¿])" +     // Al menos 1 carácter especial
                ".{5,}" +                // Al menos 4 caracteres
                "$")

        if(mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            binding.textView7.setTextColor(Color.RED)
            binding.textView7.text = "Ingrese un correo valido"
            Toast.makeText(this, "Ingrese un email valido.", Toast.LENGTH_SHORT).show()
        } else if(mNameRegister.isEmpty()) {
            Toast.makeText(this, "Enter your name...", Toast.LENGTH_SHORT).show()
        }else if (mPassword.isEmpty() || !passwordRegex.matcher(mPassword).matches()){
            binding.textView8.setTextColor(Color.RED)
            binding.textView8.text = "Almenos un caracter especial por ejemplo: -@#\$%^&+=.,|!?¿"
            Toast.makeText(this, "La contraseña es debil.", Toast.LENGTH_SHORT).show()
        } else if (mPassword != mRepeatPassword){
            binding.textView9.setTextColor(Color.RED)
            binding.textView9.text = "ERROR"
            Toast.makeText(this, "Confirma la contraseña.", Toast.LENGTH_SHORT).show()
        } else {
            createAccount(mEmail, mPassword, mNameRegister)
        }
    }
    private fun createAccount(email: String, password: String, name:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val timestamp = System.currentTimeMillis()
                    val uid = auth.uid

                    val hasMap: HashMap<String, Any?> = HashMap()
                    hasMap["uid"] = uid
                    hasMap["name"] = name
                    hasMap["email"] = email
                    hasMap["profileImage"] = ""
                    hasMap["userType"] = "user"
                    hasMap["timestamp"] = timestamp

                    val refFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Usuarios")
                    refFirebaseDatabase.child(uid!!).setValue(hasMap)
                        .addOnSuccessListener {

                            val intent = Intent(this, LoginActivity::class.java)
                            Noti()
                            startActivity(intent)
                            //startActivity(Intent(this@RegisterActivity, DashboardUserActivity::class.java))


                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "Fallo en guardar la informacion", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Contraseña o Correo incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun Noti(){
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channlId,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channlId)
                .setContentTitle("Registro Completado")
                .setContentText("Te acabas de unir a nuestra comunidad ahora inicia sesion")
                .setSmallIcon(R.drawable.logo_noti)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.logo_noti))
                .setContentIntent(pendingIntent)
        }else{
            builder = Notification.Builder(this)
                .setContentTitle("Registro Completado")
                .setContentText("Te acabas de unir a nuestra comunidad ahora inicia sesion")
                .setSmallIcon(R.drawable.logo_noti)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.logo_noti))
                .setContentIntent(pendingIntent)
        }
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.JELLY_BEAN){
            notificationManager.notify(1234,builder.build())
        }
    }
}



package com.example.anbiel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.anbiel.databinding.ActivityUbicationBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class UbicationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUbicationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animationAll()
        val mapView = findViewById<MapView>(R.id.map)
        binding.backUbicacion.setOnClickListener { finish() }

        if (mapView != null) {
            Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))

            // Configurar la fuente de los mapas
            mapView.setTileSource(TileSourceFactory.MAPNIK)

            // Configurar la posición y el nivel de zoom del mapa
            val startPoint = GeoPoint(25.539639, -103.326195)
            mapView.controller.setCenter(startPoint)
            mapView.controller.setZoom(14)
            mapView.setBuiltInZoomControls(false)
            mapView.setMultiTouchControls(true)

            // Añadir un marcador en la posición actual
            val marker = Marker(mapView)
            marker.position = startPoint
            mapView.overlays.add(marker)

        } else {
            // Mostrar un mensaje de error o registrar en el log que el objeto mapView es nulo
        }
    }
    private fun animationAll() {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.map.startAnimation(alphaAnimation)

        val scale = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = 600

        binding.backUbicacion.startAnimation(scale)


    }
}
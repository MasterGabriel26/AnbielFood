package com.example.anbiel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.anbiel.categorias.*
import com.example.anbiel.databinding.ActivityCategoryDetailBinding

class CategoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryDetailBinding
    lateinit var rvLikes: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backCategoriesAll.setOnClickListener {
            finish()
        }
        clickPhotoCategory()
        clickTextCategory()
        animationAll()
    }
    private fun clickTextCategory() {
        binding.tv1.setOnClickListener {
            val intent = Intent(this, TacosActivity::class.java)
            startActivity(intent)
        }
        binding.tv2.setOnClickListener {
            startActivity(Intent(this, HamburgerActivity::class.java))
        }
        binding.tv3.setOnClickListener {
            startActivity(Intent(this, PizzaActivity ::class.java))
        }
        binding.tv4.setOnClickListener {
            startActivity(Intent(this, BebidasActivity ::class.java))
        }
        binding.tv5.setOnClickListener {
            startActivity(Intent(this, PostresActivity ::class.java))
        }
        binding.tv6.setOnClickListener {
            startActivity(Intent(this, ShushiActivity ::class.java))
        }
        binding.tv7.setOnClickListener {
            startActivity(Intent(this, PolloActivity ::class.java))
        }
        binding.tv8.setOnClickListener {
            startActivity(Intent(this, SaladActivity ::class.java))
        }
    }
    private fun clickPhotoCategory() {
        binding.ivCTacos.setOnClickListener {
            val intent = Intent(this, TacosActivity::class.java)
            startActivity(intent)
        }
        binding.ivCHamburger.setOnClickListener {
            startActivity(Intent(this, HamburgerActivity::class.java))
        }
        binding.ivCPizza.setOnClickListener {
            startActivity(Intent(this, PizzaActivity ::class.java))
        }
        binding.ivCBebidas.setOnClickListener {
            startActivity(Intent(this, BebidasActivity ::class.java))
        }
        binding.ivCPostres.setOnClickListener {
            startActivity(Intent(this, PostresActivity ::class.java))
        }
        binding.ivCShushi.setOnClickListener {
            startActivity(Intent(this, ShushiActivity ::class.java))
        }
        binding.ivCPollo.setOnClickListener {
            startActivity(Intent(this, PolloActivity ::class.java))
        }
        binding.ivCSalad.setOnClickListener {
            startActivity(Intent(this, SaladActivity ::class.java))
        }
    }
    private fun animationAll() {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.tv1.startAnimation(alphaAnimation)
        binding.tv2.startAnimation(alphaAnimation)
        binding.tv3.startAnimation(alphaAnimation)
        binding.tv4.startAnimation(alphaAnimation)
        binding.tv5.startAnimation(alphaAnimation)
        binding.tv6.startAnimation(alphaAnimation)
        binding.tv7.startAnimation(alphaAnimation)
        binding.tv8.startAnimation(alphaAnimation)

        val scale = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = 600

        binding.card1.startAnimation(scale)
        binding.card2.startAnimation(scale)
        binding.card3.startAnimation(scale)
        binding.card4.startAnimation(scale)
        binding.card5.startAnimation(scale)
        binding.card6.startAnimation(scale)
        binding.card7.startAnimation(scale)
        binding.card8.startAnimation(scale)
    }

    override fun onResume() {
        super.onResume()
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1400
        binding.tv1.startAnimation(alphaAnimation)
        binding.tv2.startAnimation(alphaAnimation)
        binding.tv3.startAnimation(alphaAnimation)
        binding.tv4.startAnimation(alphaAnimation)
        binding.tv5.startAnimation(alphaAnimation)
        binding.tv6.startAnimation(alphaAnimation)
        binding.tv7.startAnimation(alphaAnimation)
        binding.tv8.startAnimation(alphaAnimation)

        val scale = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = 600

        binding.card1.startAnimation(scale)
        binding.card2.startAnimation(scale)
        binding.card3.startAnimation(scale)
        binding.card4.startAnimation(scale)
        binding.card5.startAnimation(scale)
        binding.card6.startAnimation(scale)
        binding.card7.startAnimation(scale)
        binding.card8.startAnimation(scale)
    }
}
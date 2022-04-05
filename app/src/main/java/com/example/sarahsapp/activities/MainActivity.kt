package com.example.sarahsapp.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.sarahsapp.R
import com.example.sarahsapp.databinding.ActivityMainTempBinding
import com.example.sarahsapp.ui.viewmodels.MainViewModel
import com.example.sarahsapp.ui.viewmodels.Student
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var splashScreenImage: ImageView
    private val splashTime = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val student = get<Student>()
//        student.beSmart()
//
//        val student2 = get<Student>()
//        student2.beSmart()
//
//        splashScreenImage = findViewById(R.id.moneySarah)
//
//        animateMoneySarah()

//        val viewModel = getViewModel<MainViewModel>()
//        viewModel.performAction()



        // TODO: calling CoinbaseProFragment
        val binding: ActivityMainTempBinding = ActivityMainTempBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    private fun animateMoneySarah(){
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_animation)
        splashScreenImage.animation = rotate
    }
}
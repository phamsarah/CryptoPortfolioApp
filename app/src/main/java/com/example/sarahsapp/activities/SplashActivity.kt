package com.example.sarahsapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.sarahsapp.R


class SplashActivity : Activity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)



        finish()
    }
}
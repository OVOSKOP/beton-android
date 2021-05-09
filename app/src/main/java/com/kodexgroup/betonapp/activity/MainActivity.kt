package com.kodexgroup.betonapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kodexgroup.betonapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BetonApp_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
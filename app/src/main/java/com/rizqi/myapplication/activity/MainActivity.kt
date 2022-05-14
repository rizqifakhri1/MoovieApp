package com.rizqi.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.rizqi.myapplication.R
import com.rizqi.myapplication.lightStatusBar
import com.rizqi.myapplication.setFullScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Turn Off dark mode

        setFullScreen(window)
        lightStatusBar(window)
    }
}
package com.example.studentregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// xml -> view
// Activity -> view controller

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
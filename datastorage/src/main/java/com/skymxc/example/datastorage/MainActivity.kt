package com.skymxc.example.datastorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.test_btn_app_specific).setOnClickListener {
            val intent = Intent(this, AppSpecificActivity::class.java)
            startActivity(intent)
        }
    }


}
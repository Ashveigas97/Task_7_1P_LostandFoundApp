package com.example.lostandfoundapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreateAdvert: Button
    private lateinit var btnShowItems: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreateAdvert = findViewById(R.id.btnCreateAdvert)
        btnShowItems = findViewById(R.id.btnShowItems)

        btnCreateAdvert.setOnClickListener {
            val intent = Intent(this, NewAdvertActivity::class.java)
            startActivity(intent)
        }

        btnShowItems.setOnClickListener {
            val intent = Intent(this, ItemsListActivity ::class.java)
            startActivity(intent)
        }
    }
}

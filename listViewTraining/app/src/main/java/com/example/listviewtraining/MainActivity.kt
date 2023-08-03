package com.example.listviewtraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView:ListView = findViewById(R.id.list_item)

        val rainbow = arrayListOf<String>("Красный", "желтый", "зеленый", "голубой", "синий")

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,rainbow)

        listView.adapter = arrayAdapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, p2, p3 -> Toast.makeText(this@MainActivity, rainbow[p2], Toast.LENGTH_SHORT).show() }
    }
}
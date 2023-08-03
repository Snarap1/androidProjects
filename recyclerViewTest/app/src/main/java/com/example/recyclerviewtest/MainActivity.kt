package com.example.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
   lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerItems = arrayListOf<RecyclerViewItem>(
            RecyclerViewItem(R.drawable.baseline_sentiment_satisfied_alt_24,"Happy", "life is fun" ),
            RecyclerViewItem(R.drawable.baseline_sentiment_very_dissatisfied_24,"Sad", "life is sad" ),
            RecyclerViewItem(R.drawable.baseline_sentiment_neutral_24,"Neutral", "life is life" )
        )

// инициализируем recyclerView созданный в начале. ID RV из activity_main
        recyclerView = findViewById(R.id.recyclerView)

// если размер фиксированный и не будет меняться.
// то ставим эту команду. Так буде выше производительность
        recyclerView.setHasFixedSize(true)

// создаём адаптер и в качестве параметра  наш arrayList начальный
        val adapter = RecyclerViewAdapter(recyclerItems)
// создаём LayoutManager, и в качестве параметра this
        val layoutManager = LinearLayoutManager(this)

//присваиваем адаптер и лэйаутМэнеджер нашему recyclerView
        recyclerView.adapter=  adapter
        recyclerView.layoutManager= layoutManager
    }

}
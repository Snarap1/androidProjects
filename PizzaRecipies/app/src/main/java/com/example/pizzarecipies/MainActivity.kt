package com.example.pizzarecipies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.pizzarecipies.Utils.Utils

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pizzaRecipeItems = arrayListOf<PizzaRecipeItem>(
            PizzaRecipeItem(R.drawable.pizza_1,Utils.Pizza1_Title, Utils.Pizza1_Desc, Utils.Pizza1_Recipe),
            PizzaRecipeItem(R.drawable.pizza_2,Utils.Pizza2_Title, Utils.Pizza2_Desc, Utils.Pizza2_Recipe),
            PizzaRecipeItem(R.drawable.pizza_3,Utils.Pizza3_Title, Utils.Pizza3_Desc, Utils.Pizza3_Recipe),
            PizzaRecipeItem(R.drawable.pizza_4,Utils.Pizza4_Title, Utils.Pizza4_Desc, Utils.Pizza4_Recipe),
            PizzaRecipeItem(R.drawable.pizza_5,Utils.Pizza5_Title, Utils.Pizza5_Desc, Utils.Pizza5_Recipe),
            PizzaRecipeItem(R.drawable.pizza_6,Utils.Pizza6_Title, Utils.Pizza6_Desc, Utils.Pizza6_Recipe),
            PizzaRecipeItem(R.drawable.pizza_7,Utils.Pizza7_Title, Utils.Pizza7_Desc, Utils.Pizza7_Recipe),
            PizzaRecipeItem(R.drawable.pizza_8,Utils.Pizza8_Title, Utils.Pizza8_Desc, Utils.Pizza8_Recipe),
            PizzaRecipeItem(R.drawable.pizza_9,Utils.Pizza9_Title, Utils.Pizza9_Desc, Utils.Pizza9_Recipe),
            PizzaRecipeItem(R.drawable.pizza_10,Utils.Pizza10_Title, Utils.Pizza10_Desc, Utils.Pizza10_Recipe),
        )

        recyclerView = findViewById(R.id.pizzaRV)
        recyclerView.setHasFixedSize(true)

        val adapter = RecyclerViewAdapter(pizzaRecipeItems, this)
        val layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}
package com.example.pizzarecipies

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.MovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class recipe : AppCompatActivity() {

    lateinit var imageIV:ImageView
    lateinit var titleTV:TextView
    lateinit var recipeTV: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        imageIV = findViewById(R.id.pizzaImageView)
        titleTV  = findViewById(R.id.titleTextView)
        recipeTV = findViewById(R.id.recipeTextView)

        recipeTV.movementMethod = ScrollingMovementMethod()
        if(intent!= null){
            imageIV.setImageResource(intent.getIntExtra("imageResource",0))
            titleTV.text = intent.getStringExtra("title")
            recipeTV.text = intent.getStringExtra("recipe")
        }
    }

    fun shareRecipe(view: View) {
//        val share = Intent.createChooser(Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_TEXT, "Hey, im sharing recipe of $titleTV: \n  $recipeTV" )
//
//        }, null)
//        startActivity(share)


        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey, im sharing recipe of ${titleTV.text}:" +
                    " \n \n ${recipeTV.text}" )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }
}
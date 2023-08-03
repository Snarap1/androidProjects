package com.example.habbittracker

import android.graphics.Bitmap

data class Habit(val title:String, val description:String, val image: Bitmap)

/*
fun getSampleHabits(): List<Habit>{
    return listOf(
        Habit("Go for a walk",
            "A nice walk in the sun gets u rdy for the day ahead",
            R.drawable.walk),
        Habit("Drink a glass of water",
        "a refreshing glass of water gets u hydrated",
        R.drawable.water)
    )
}*/

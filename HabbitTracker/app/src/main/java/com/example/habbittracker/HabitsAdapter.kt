package com.example.habbittracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitsAdapter(val habits: List<Habit>)
    : RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {


    class  HabitViewHolder(val card: View):RecyclerView.ViewHolder(card){
        var tvTitle: TextView = card.findViewById(R.id.tv_title)
        var tvDescription: TextView = card.findViewById(R.id.tv_description)
        var ivIcon:ImageView = card.findViewById(R.id.iv_icon)
    }


    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
    if(holder !=null) {
        holder.tvTitle.text = habits[position].title
        holder.tvDescription.text = habits[position].description
        holder.ivIcon.setImageBitmap(habits[position].image)
    }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_cart,
            parent,false)
        return HabitViewHolder(view)
    }

    override fun getItemCount(): Int {
        return habits.size
    }
}

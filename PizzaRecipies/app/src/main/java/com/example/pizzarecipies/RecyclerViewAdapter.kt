package com.example.pizzarecipies

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(var arrayList: ArrayList<PizzaRecipeItem>, var context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, var context: Context, var arrayList: ArrayList<PizzaRecipeItem>) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var pizzaView: ImageView
        var title: TextView
        var description: TextView
        init {
            itemView.setOnClickListener(this)
            pizzaView = itemView.findViewById(R.id.pizzaIV)
            title = itemView. findViewById(R.id.titleTV)
            description = itemView.findViewById(R.id.descriptionTV)
        }

        @SuppressLint("SuspiciousIndentation")
        override fun onClick(p0: View?) {
            val position = adapterPosition
            val pizzaRecipeItem = arrayList[position]

          val intent: Intent = Intent(context ,recipe::class.java)
            intent.putExtra("imageResource", pizzaRecipeItem.imageResource)
            intent.putExtra("title",pizzaRecipeItem.title)
            intent.putExtra("description", pizzaRecipeItem.description)
            intent.putExtra("recipe", pizzaRecipeItem.recipe)

            context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view :View = LayoutInflater.from(parent.context)
            .inflate(R.layout.pizza_item, parent,false)

        return ViewHolder(view,context,arrayList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val PizzaRecipeItem = arrayList[position]

        holder.pizzaView.setImageResource(PizzaRecipeItem.imageResource)
        holder.title.text = PizzaRecipeItem.title
        holder.description.text = PizzaRecipeItem.description
    }

    override fun getItemCount(): Int {
     return  arrayList.size
    }



}
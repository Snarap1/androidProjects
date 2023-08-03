package com.example.recyclerviewtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(var arrayList: ArrayList<RecyclerViewItem>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class  ViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView){
        // создаём переменные
        var imageView: ImageView
        var textView1: TextView
        var textView2: TextView
        // в конструкторе связываем их с разметкой
        init {
            imageView = itemView.findViewById(R.id.image)
            textView1 = itemView.findViewById(R.id.tv1)
            textView2 = itemView.findViewById(R.id.tv2)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
        // тут указываем наш item_recycler
            .inflate(R.layout.recycler_view_item, parent,false)

        // создаём вью объект и ретурним его,
        //  в параметр передаём вышесозданный view
        val viewHolder: ViewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // передаём отдельный элемент аррайЛиста в объект recyclerViewItem
        val recyclerViewItem: RecyclerViewItem = arrayList[position]

        // потом к холдеру - присваиваем его значения
        holder.imageView.setImageResource(recyclerViewItem.imageResource)
        holder.textView1.text = recyclerViewItem.text1
        holder.textView2.text = recyclerViewItem.text2
    }

    override fun getItemCount(): Int = arrayList.size

}

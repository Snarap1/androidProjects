package com.example.chatapp.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.models.Message

class MessageAdapter(context: Context, resource:Int,messages:List<Message>) : ArrayAdapter<Message>(context,resource,messages) {

    override fun getView(position: Int,convertView: View?, parent: ViewGroup): View {
       var  convertView1 = convertView
        if(convertView1 == null){
            convertView1 = (context as Activity).layoutInflater.inflate(R.layout.message_item,parent,false)
        }tu

        val photoImageView: ImageView = convertView1!!.findViewById(R.id.photoImageView)
        val textView:TextView = convertView1.findViewById(R.id.textTextView)
        val nameTextView:TextView = convertView1.findViewById(R.id.nameTextView)

        val message: Message = getItem(position)!!

        val isText:Boolean = message.imageUrl.isEmpty()
        if(isText){
            textView.visibility = View.VISIBLE
            photoImageView.visibility = View.GONE
            textView.text = message.text
        }else{
            textView.visibility = View.GONE
            photoImageView.visibility = View.VISIBLE
            Glide.with(photoImageView.context).load(message.imageUrl).into(photoImageView)
        }
        nameTextView.setText(message.name)

        return convertView1
    }
}
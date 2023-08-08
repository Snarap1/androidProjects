package com.example.sportclubapp.Data

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.sportclubapp.R

class MemberAdapter(context: Context, cursor: Cursor?): CursorAdapter(context,cursor,0) {
    val handler = ClubDbOpenHelper(context)

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.member_layout,parent,false)
    }

    @SuppressLint("Range")
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val firstNameTV:TextView = view!!.findViewById(R.id.firstNameTV)
        val lastNameTV:TextView = view!!.findViewById(R.id.lastNameTV)
        val healthGroupTV:TextView =  view!!.findViewById(R.id.groupTV)

        val firstName = cursor!!.getString(cursor.getColumnIndex(ClubContract.MemberEntry.KEY_FIRST_NAME))
        val lastName = cursor.getString(cursor.getColumnIndex(ClubContract.MemberEntry.KEY_LAST_NAME))
        val healthGroup = cursor.getString(cursor.getColumnIndex(ClubContract.MemberEntry.KEY_HEALTH_GROUP))

        firstNameTV.text = firstName
        lastNameTV.text = lastName
        healthGroupTV.text = healthGroup
    }
}
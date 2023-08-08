package com.example.sportclubapp

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.sportclubapp.Data.ClubContract.*
import com.example.sportclubapp.Data.MemberAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    lateinit var dataListView:ListView
    lateinit var memberAdapter: MemberAdapter

    companion object{
        const val MEMBER_LOADER = 123;
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataListView = findViewById(R.id.dataListView)

        val floatingActionButton: FloatingActionButton = findViewById(R.id.floatingActionButton)

        floatingActionButton.setOnClickListener {
            val i1:Intent = Intent(this, AddMemberActivity::class.java)
            startActivity(i1) }

        memberAdapter = MemberAdapter(this,null)
        dataListView.adapter = memberAdapter
        LoaderManager.getInstance(this).initLoader(MEMBER_LOADER,null,this)

        dataListView.setOnItemClickListener { parent, view, position, id ->
            val i2: Intent = Intent(this@MainActivity, AddMemberActivity::class.java)
            val currURI: Uri = ContentUris.withAppendedId(MemberEntry.CONTENT_URI, id)
            i2.setData(currURI)
            startActivity(i2)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val arr = arrayOf(MemberEntry.KEY_ID, MemberEntry.KEY_FIRST_NAME, MemberEntry.KEY_LAST_NAME, MemberEntry.KEY_HEALTH_GROUP, )
        val cursorLoader: CursorLoader =  CursorLoader(this,MemberEntry.CONTENT_URI,arr,null,null,null)
        return cursorLoader
    }



    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        memberAdapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        memberAdapter.swapCursor(null)
    }
}
package com.example.sportclubapp.Data
import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



@SuppressLint("NewApi")
class ClubDbOpenHelper(context: Context?) : SQLiteOpenHelper( context,  ClubContract.DB_NAME,  null, ClubContract.DB_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_MEMBER_TABLE = "CREATE TABLE ${ClubContract.MemberEntry.TABLE_NAME} (" +
                "${ClubContract.MemberEntry.KEY_ID} INTEGER PRIMARY KEY," +
                "${ClubContract.MemberEntry.KEY_FIRST_NAME} TEXT," +
                "${ClubContract.MemberEntry.KEY_LAST_NAME} TEXT," +
                "${ClubContract.MemberEntry.KEY_GENDER} INTEGER NOT NULL," +
                "${ClubContract.MemberEntry.KEY_HEALTH_GROUP} TEXT )"

        p0?.execSQL(CREATE_MEMBER_TABLE)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS ${ClubContract.MemberEntry.TABLE_NAME}")
        onCreate(p0)
    }
}
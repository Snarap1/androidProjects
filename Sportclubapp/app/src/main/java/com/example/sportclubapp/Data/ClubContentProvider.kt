package com.example.sportclubapp.Data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import com.example.sportclubapp.Data.ClubContract.*
import java.lang.IllegalArgumentException

private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    /*
     * The calls to addURI() go here for all the content URI patterns that the provider
     * recognizes. For this snippet, only the calls for table 3 are shown.
     */

    /*
     * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
     * in the path.
     */
    addURI(ClubContract.AUTHORITY, ClubContract.PATH_MEMBERS, 111)

    /*
     * Sets the code for a single row to 2. In this case, the # wildcard is
     * used. content://com.example.app.provider/table3/3 matches, but
     * content://com.example.app.provider/table3 doesn't.
     */
    addURI(ClubContract.AUTHORITY, ClubContract.PATH_MEMBERS+"/#", 222)
}


class ClubContentProvider : ContentProvider() {
    private lateinit var dbOpenHelper:ClubDbOpenHelper

    override fun onCreate(): Boolean {
        dbOpenHelper = ClubDbOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
       selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db:SQLiteDatabase = dbOpenHelper.readableDatabase
        val cursor:Cursor
        val match = sUriMatcher.match(uri)
        cursor = when(match){
            111->{
                db.query(MemberEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder)
            }

            222->{
                val selection1: String = MemberEntry.KEY_ID+"=?"
                val selectionArgs1: Array<out String> = arrayOf( "${ContentUris.parseId(uri)}")
                db.query(MemberEntry.TABLE_NAME,projection,selection1,selectionArgs,null,null,sortOrder)
            }

            else-> {
                throw IllegalArgumentException("Cant query incorrect uri $uri")
            }
        }
        cursor.setNotificationUri(context!!.contentResolver,uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val firstName = values!!.getAsString(MemberEntry.KEY_FIRST_NAME)
            if (firstName==null) throw IllegalArgumentException("U have to input first name")

        val lastName = values.getAsString(MemberEntry.KEY_LAST_NAME)
            ?:throw IllegalArgumentException("U have to input last name")

        val gender = values.getAsInteger(MemberEntry.KEY_GENDER)
        if(gender==null || !(gender == MemberEntry.GENDER_FEMALE
                    || gender== MemberEntry.GENDER_MALE || gender == MemberEntry.GENDER_UNKNOWN)){
            throw IllegalArgumentException("U have to input correct gender")
        }

        val healthGroup = values.getAsString(MemberEntry.KEY_HEALTH_GROUP)
            ?: throw IllegalArgumentException("U have to input health group")

        val db = dbOpenHelper.writableDatabase

        val match = sUriMatcher.match(uri)
       when(match){
           111->{
               val id:Long = db.insert(MemberEntry.TABLE_NAME,null,values)
               if(id.equals(-1)){
                    Log.e("insertMethod", "data insertion failed for $uri")
                   return  null
               }
               context!!.contentResolver.notifyChange(uri,null)

               return ContentUris.withAppendedId(uri,id)
           }

            else-> {
                throw IllegalArgumentException("Insertion of data failed for uri $uri")
            }
       }

    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {

        if(values?.containsKey(MemberEntry.KEY_FIRST_NAME) == true){
            val firstName = values.getAsString(MemberEntry.KEY_FIRST_NAME)
                ?: throw IllegalArgumentException("U have to input first name")
        }

        if (values?.containsKey(MemberEntry.KEY_LAST_NAME) == true){
            val lastName = values.getAsString(MemberEntry.KEY_LAST_NAME)
                ?:throw IllegalArgumentException("U have to input last name")
        }

        if (values?.containsKey(MemberEntry.KEY_GENDER) == true){
            val gender = values.getAsInteger(MemberEntry.KEY_GENDER)
            if(gender==null || !(gender == MemberEntry.GENDER_FEMALE
                        || gender== MemberEntry.GENDER_MALE || gender == MemberEntry.GENDER_UNKNOWN)){
                throw IllegalArgumentException("U have to input correct gender")
            }
        }

        if (values?.containsKey(MemberEntry.KEY_HEALTH_GROUP) == true)
        {
            val healthGroup = values.getAsString(MemberEntry.KEY_HEALTH_GROUP)
                ?: throw IllegalArgumentException("U have to input health group")
        }

        val db = dbOpenHelper.writableDatabase
        val rowsUpdated:Int

        val match = sUriMatcher.match(uri)
        when(match) {
            111 -> {
               rowsUpdated =   db.update(MemberEntry.TABLE_NAME,values,selection,selectionArgs)
                if(rowsUpdated!=0) context!!.contentResolver.notifyChange(uri,null)

                return rowsUpdated
            }

            222 -> {
                val selection1: String = MemberEntry.KEY_ID + "=?"
                val selectionArgs1: Array<out String> = arrayOf("${ContentUris.parseId(uri)}")
                 rowsUpdated = db.update(MemberEntry.TABLE_NAME,values,selection1,selectionArgs1)
                if(rowsUpdated!=0) context!!.contentResolver.notifyChange(uri,null)
                return rowsUpdated
            }

            else -> {
                throw IllegalArgumentException("Cant update,  incorrect uri $uri")
            }
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper.writableDatabase
        val rowsDeleted:Int

        val match = sUriMatcher.match(uri)
        when(match) {
            111 -> {
               rowsDeleted =  db.delete(MemberEntry.TABLE_NAME,selection,selectionArgs)
                if(rowsDeleted!=0) context!!.contentResolver.notifyChange(uri,null)
                return rowsDeleted
            }

            222 -> {
                val selection1: String = MemberEntry.KEY_ID + "=?"
                val selectionArgs1: Array<out String> = arrayOf("${ContentUris.parseId(uri)}")
                rowsDeleted =  db.delete(MemberEntry.TABLE_NAME,selection1,selectionArgs1)
                if(rowsDeleted!=0) context!!.contentResolver.notifyChange(uri,null)
                return rowsDeleted
            }

            else -> {
                throw IllegalArgumentException("Cant delete, incorrect uri $uri")
            }
        }
    }



    override fun getType(uri: Uri): String {
        val match = sUriMatcher.match(uri)
        when(match) {
            111 -> {
                return  MemberEntry.CONTENT_MULTIPLE_ITEMS
            }

            222 -> {
                return MemberEntry.CONTENT_SINGLE_ITEMS
            }

            else -> {
                throw IllegalArgumentException("UNKNOWN URI:  $uri")
            }
        }
    }
}
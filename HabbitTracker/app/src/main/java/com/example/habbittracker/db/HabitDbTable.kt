package com.example.habbittracker.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.habbittracker.Habit
import com.example.habbittracker.db.HabitEntry.DESCR_COL
import com.example.habbittracker.db.HabitEntry.IMAGE_COL
import com.example.habbittracker.db.HabitEntry.TABLE_NAME
import com.example.habbittracker.db.HabitEntry.TITLE_COL
import com.example.habbittracker.db.HabitEntry._ID
import java.io.ByteArrayOutputStream

class HabitDbTable(context: Context)
{
    private  val dbHelper = HabitTrainerDb(context)

    val TAG = HabitDbTable::class.java.simpleName

    fun  store(habit: Habit): Long{
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with(values){
            put(TITLE_COL,habit.title)
            put(DESCR_COL,habit.description)
            put(IMAGE_COL,toByteArray(habit.image))
        }

       val id = db.transaction {  insert(TABLE_NAME, null,values ) }

        Log.d(TAG,"Stored new habit to DB")

        return id
    }

    @SuppressLint("Range")
    fun readAllHabits():List<Habit>{

        val columns = arrayOf(_ID, TITLE_COL, DESCR_COL, IMAGE_COL)
        val db = dbHelper.readableDatabase

        val order = "$_ID ASC"

        val cursor =  db.doQuery(TABLE_NAME,columns, orderBy = order)

        val habits = parseHabitsFrom(cursor)

        return habits
    }

    private fun parseHabitsFrom(cursor: Cursor): MutableList<Habit> {
        val habits = mutableListOf<Habit>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(TITLE_COL)
            val description = cursor.getString(DESCR_COL)
            val bitmap = cursor.getBitMap(IMAGE_COL)
            habits.add(Habit(title, description, bitmap))
        }
        cursor.close()
        return habits
    }

    private fun toByteArray(image: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 0,stream)
        return stream.toByteArray()
    }


}
@SuppressLint("Range")
private fun  Cursor.getString(columnName: String):String = this.getString(getColumnIndex(columnName))
@SuppressLint("Range")
private  fun Cursor.getBitMap(columnName: String):Bitmap{
    val bytes = getBlob(getColumnIndex(columnName))
    return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
}

private fun SQLiteDatabase.doQuery(table: String, columns: Array<String>,selection:String? = null,
selectionArgs:Array<String>?=null,groupBy: String?=null, having:String?=null,
orderBy: String?=null):Cursor {
 return   query(table,columns, selection, selectionArgs, groupBy, having, orderBy)
}

private inline fun <T>SQLiteDatabase.transaction(function: SQLiteDatabase.()-> T):T{
    beginTransaction()
    val result = try {
       val returnValue = function()
        setTransactionSuccessful()
        returnValue
    }
    finally {
        endTransaction()
    }
    close()
    return result
}


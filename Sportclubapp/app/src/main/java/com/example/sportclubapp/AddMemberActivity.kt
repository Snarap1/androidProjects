package com.example.sportclubapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toolbar.OnMenuItemClickListener
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.sportclubapp.Data.ClubContract.MemberEntry
import com.google.android.material.appbar.MaterialToolbar

class AddMemberActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor>{
 private   lateinit var firstNameEditText:EditText
 private   lateinit var lastNameEditText: EditText
 private   lateinit var groupEditText: EditText
 private  lateinit var genderSpinner: Spinner
 private var gender:Int = 0
    var currMemberUri: Uri? = null

    companion object{
        const val EDIT_MEMBER_LOADER = 111
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        val materialToolbar:MaterialToolbar = findViewById(R.id.toolBar)
        startToolBar()
        val intent1 = getIntent()
       currMemberUri = intent1.data

        if (currMemberUri == null){
            materialToolbar.title = "ADSasda"
        }else{
            materialToolbar.title = "Edit member"
        }

        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        groupEditText = findViewById(R.id.groupEditText)


        genderSpinner = findViewById(R.id.genderSpinner)

        val genders = arrayListOf<String>("Unknown","Male", "Female")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
       spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = spinnerAdapter

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedGender:String = parent?.getItemAtPosition(position).toString()
                if (!TextUtils.isEmpty(selectedGender))
                {
                 gender =  when{
                        selectedGender.equals("Male")-> MemberEntry.GENDER_MALE
                        selectedGender.equals("Female") -> MemberEntry.GENDER_FEMALE
                        else -> {MemberEntry.GENDER_UNKNOWN}
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                gender = 0
            }
        }

        LoaderManager.getInstance(this).initLoader(EDIT_MEMBER_LOADER,null,this)

    }

    private  fun startToolBar()
    {
        val materialToolbar:MaterialToolbar = findViewById(R.id.toolBar)
        materialToolbar.inflateMenu(R.menu.edit_member_menu)
        materialToolbar.setOnMenuItemClickListener(object :OnMenuItemClickListener,
            Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.save_member -> {
                        insertMember()
                        return true
                    }
                    R.id.delete_member -> return true
                    android.R.id.home-> {
                        NavUtils.navigateUpFromSameTask(this@AddMemberActivity)
                        return true
                    }
                }
                return true
            }
        })


    }

    @SuppressLint("SuspiciousIndentation")
    private  fun insertMember(){
        val firstName:String = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val healthGroup = groupEditText.text.toString().trim()

        val contentValues = ContentValues()
        contentValues.put(MemberEntry.KEY_FIRST_NAME, firstName)
        contentValues.put(MemberEntry.KEY_LAST_NAME, lastName)
        contentValues.put(MemberEntry.KEY_HEALTH_GROUP, healthGroup)
        contentValues.put(MemberEntry.KEY_GENDER,gender )

        val currMemberUri: Uri? = intent.data
        if(currMemberUri==null){
            val contentResolver = contentResolver
            val uri =  contentResolver.insert(MemberEntry.CONTENT_URI,contentValues)

            if (uri==null)
            {
                Toast.makeText(this, "Insertion of data in the table failed", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
            }
        }else{
         val  rowsChanged = contentResolver.update(currMemberUri,contentValues,null,null)
            if(rowsChanged==0){
                Toast.makeText(this, "Saving of data failed", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Member info updated", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(
            MemberEntry.KEY_ID,
            MemberEntry.KEY_FIRST_NAME,
            MemberEntry.KEY_LAST_NAME,
            MemberEntry.KEY_GENDER,
            MemberEntry.KEY_HEALTH_GROUP
        )

        return CursorLoader(
            this,
            currMemberUri!!,
            projection,
            null, null, null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        if(cursor.moveToFirst())
        {
            val firstNameIdx = cursor.getColumnIndex(MemberEntry.KEY_FIRST_NAME)
            val lastNameIdx = cursor.getColumnIndex(MemberEntry.KEY_LAST_NAME)
            val genderIdx = cursor.getColumnIndex(MemberEntry.KEY_GENDER)
            val healthGroupIdx = cursor.getColumnIndex(MemberEntry.KEY_HEALTH_GROUP)

            val firstName:String = cursor.getString(firstNameIdx)
            val lastName:String = cursor.getString(lastNameIdx)
            val gender:Int = cursor.getInt(genderIdx)
            val healthGroup:String = cursor.getString(healthGroupIdx)

            firstNameEditText.setText(firstName)
            lastNameEditText.setText(lastName)
            groupEditText.setText(healthGroup)

            when(gender){
                MemberEntry.GENDER_MALE -> genderSpinner.setSelection(1)
                MemberEntry.GENDER_FEMALE -> genderSpinner.setSelection(2)
                MemberEntry.GENDER_UNKNOWN -> genderSpinner.setSelection(0)
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

}



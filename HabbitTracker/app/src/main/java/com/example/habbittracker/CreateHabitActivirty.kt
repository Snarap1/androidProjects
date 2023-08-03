package com.example.habbittracker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.ImageBitmap
import com.example.habbittracker.db.HabitDbTable

class CreateHabitActivirty : AppCompatActivity() {
    private  var TAG = CreateHabitActivirty::class.java.simpleName
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var imageBitmap: Bitmap? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit_activirty)
        val ivImage: ImageView = findViewById(R.id.iv_image)


        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK && result.data != null) {
                val selectedImageUri: Uri? = result.data?.data
                val bitmap: Bitmap? = selectedImageUri?.let { getBitmapFromUri(it) }
                if (bitmap != null) {
                    this.imageBitmap = bitmap
                    ivImage.setImageBitmap(bitmap)
                }
            }
        }

        val pickImageButton: Button = findViewById(R.id.pickImageButton)

        pickImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

    }
    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val options = BitmapFactory.Options().apply {
                // Adjust the options based on your requirements
                inSampleSize = 1 // Set the image's sample size if needed
            }
            BitmapFactory.decodeStream(inputStream, null, options)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun storeHabit(view: View) {
       val etTitle:TextView = findViewById(R.id.edit_title)
        val etDesc: TextView = findViewById(R.id.edit_desc)

        if(etTitle.text.isBlank()|| etDesc.text.isBlank()){
            Log.d(TAG,"no habit stored: tittle or description missing")
            displayErrorMessage("Your habit need an engaging title and description")
        }else if(imageBitmap == null){
            Log.d(TAG, "No habit  stored: image missing")
            displayErrorMessage("add a motivating picture to your habbit")
            return
        }

        //todo store in database

      val title =   etTitle.text.toString()
        val description  = etDesc.text.toString()
        val habit = Habit(title,description, imageBitmap!!)

        val id = HabitDbTable(this).store(habit)
        if(id == -1L ){
            displayErrorMessage("habit coudlnt be stored .... lets not make this a habit")
        }else{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayErrorMessage(s: String) {
        val tvError:TextView = findViewById(R.id.tv_error)
        tvError.text = s
        tvError.visibility = View.VISIBLE
    }



}

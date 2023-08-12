package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar
import com.example.chatapp.adapters.MessageAdapter
import com.example.chatapp.models.Message
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var messageListView: ListView
    private lateinit var adapter:MessageAdapter
    private  lateinit var progressBar:ProgressBar
    private lateinit var sendImageButton:ImageView
    private lateinit var sendMessageBtn:Button
    private lateinit var msgEditText:EditText

    private lateinit var userName:String


    var  database = Firebase.database("https://chatapp-686ae-default-rtdb.europe-west1.firebasedatabase.app/")
    lateinit var messagesRef: DatabaseReference

    lateinit var messagesChildEventListener: ChildEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userName = "Default User"
        messageListView = findViewById(R.id.msgListView)

        var messages:List<Message> = arrayListOf()

        adapter = MessageAdapter(this,R.layout.message_item,messages )
        messageListView.adapter = adapter

        messagesRef = database.getReference("messages")


        progressBar = findViewById(R.id.progressBar)
        sendImageButton = findViewById(R.id.sendPhotoBtn)
        sendMessageBtn = findViewById(R.id.sendMessageBtn)
        msgEditText = findViewById(R.id.messageText)

        progressBar.visibility = ProgressBar.INVISIBLE

        msgEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendMessageBtn.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendMessageBtn.isEnabled = p0?.trim()?.length!! >0
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("EditText", "TextChanged")
            }
        })

        sendMessageBtn.setOnClickListener( View.OnClickListener { view ->
            val message = Message()
            message.text = msgEditText.text.toString()
            message.name = userName

            messagesRef.push().setValue(message)

            msgEditText.setText("")

        })

        sendImageButton.setOnClickListener(View.OnClickListener { view ->  })

        messagesChildEventListener = object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message:Message
                    = snapshot.getValue(Message::class.java)!!

                adapter.add(message)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        messagesRef.addChildEventListener(messagesChildEventListener)
    }
}
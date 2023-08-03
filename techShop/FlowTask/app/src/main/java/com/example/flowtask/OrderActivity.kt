package com.example.flowtask

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class OrderActivity : AppCompatActivity() {
   var addresses = arrayOf("pascha16032005@gmail.com")
    private val subject = "Order from E-shop"
     var emailText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val receiveOrderIntent = getIntent()
        val userName = receiveOrderIntent.getStringExtra("userNameIntent")
        val goodsName = receiveOrderIntent.getStringExtra("goodsNameIntent")
        val quantity = receiveOrderIntent.getIntExtra("quantityIntent",0)
        val orderPrice = receiveOrderIntent.getDoubleExtra("orderPriceIntent",0.0)

        val orderTV:TextView = findViewById(R.id.orderTV)

        emailText = "$userName  : $goodsName  : $quantity : $orderPrice"
        orderTV.setText(emailText)
    }


    fun  submitOrder(view: View){
        val email: EditText = findViewById(R.id.editTextTextEmailAddress)
        addresses = arrayOf(email.text.toString())

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, emailText)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}





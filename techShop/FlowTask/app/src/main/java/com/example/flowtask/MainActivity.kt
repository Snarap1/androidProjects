package com.example.flowtask

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.example.flowtask.models.Order

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    var quantity = 0
    private val goodsMap = hashMapOf<String,Double>()
    private val imageMap = hashMapOf<String,Int>()
    var price:Double = 0.0
    lateinit var spinner:Spinner
    lateinit var editTextView: EditText
    lateinit var goodsName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextView = findViewById(R.id.editTextText)

        createSpinner()
        initMaps()
    }

    fun initMaps(){
        goodsMap.put("Laptop", 3000.0)
        goodsMap.put("TV", 2000.0)
        goodsMap.put("Monitor", 500.0)

        imageMap.put("Laptop",R.drawable.acerlaptop)
        imageMap.put("TV",R.drawable.tv)
        imageMap.put("Monitor", R.drawable.monitor)
    }

    fun createSpinner(){
        spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this
        val spinnerArrayList = arrayListOf("Laptop","TV", "Monitor")
        val spinnerAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            spinnerArrayList)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
    }

    fun increaseQuantity(view: View){
        quantity++
        val qView: TextView = findViewById(R.id.qView)
        qView.text = quantity.toString()
        setPrice()
    }

    fun decreaseQuantity(view: View){
        if(quantity!=0){
            quantity--
            val qView: TextView = findViewById(R.id.qView)
            qView.text = quantity.toString()
            setPrice()
        }
        else{
            quantity=0
        }
    }


    fun setPrice(){
        var goodsName: String = spinner.selectedItem.toString()
        price = goodsMap.get(goodsName)!!
        var priceView:TextView = findViewById(R.id.priceView)
        priceView.setText("" + quantity * price + "$")
    }

    fun setImage(){
        goodsName = spinner.selectedItem.toString()
        var itemImage:ImageView = findViewById(R.id.itemImage)
        itemImage.setImageResource(imageMap.get(goodsName)!!)
    }

    // implementing adapter methods
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        setPrice()
        setImage()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun addToCart(view: View) {
        val order: Order = Order()

        order.userName = editTextView.text.toString()
        order.goodsName = goodsName
        order.quantity = quantity
        order.orderPrice = price * quantity

        Log.d("printCartInfo", order.userName
                +" "+ order.goodsName
                +" "+ order.quantity
                +" "+order.orderPrice)

        val orderIntent = Intent(this,OrderActivity::class.java)
        orderIntent.putExtra("userNameIntent", order.userName);
        orderIntent.putExtra("goodsNameIntent",order.goodsName)
        orderIntent.putExtra("quantityIntent", order.quantity)
        orderIntent.putExtra("orderPriceIntent", order.orderPrice)

        startActivity(orderIntent)
    }


}

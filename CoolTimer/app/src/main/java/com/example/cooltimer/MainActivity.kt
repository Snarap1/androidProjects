package com.example.cooltimer

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import android.widget.Toolbar.OnMenuItemClickListener
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

   lateinit var timer:CountDownTimer
    lateinit var seekBar:SeekBar
     var isTimerOn:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolBar()

        seekBar = findViewById(R.id.seekBar)
        seekBar.max = 600
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                seekBar.progress = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {


                val value = seekBar.progress.toLong() * 1000
                updateTimer(value)
            }
        })

    }

    fun startStop(view: View) {
        val btn:Button = findViewById(R.id.start)
        val seekBar: SeekBar = findViewById(R.id.seekBar)

        if(!isTimerOn) {
            seekBar.isVisible = false
            btn.text = "pause"
            isTimerOn = true

            timer = object: CountDownTimer(seekBar.progress.toLong()*1000,1100) {
                override fun onTick(p0: Long) {
                    updateTimer(p0)
                }
                override fun onFinish() {

                    var sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(applicationContext)

                    if (sharedPreferences.getBoolean("enable_sound",true)){
                        val mediaPlayer = MediaPlayer.create(applicationContext,R.raw.bell_sound)
                        mediaPlayer.start()
                    }



                    resetTimer()
                    Toast.makeText(this@MainActivity, "Finished", Toast.LENGTH_SHORT).show()
                }
            }
            timer.start()
        }
        else{
          resetTimer()
        }

    }

    private  fun updateTimer(p0:Long){
        val minutes: Long = (p0 / 1000) / 60
        val seconds: Long = (p0 / 1000) - (minutes * 60)

        var minutesString = ""
        var secondsString = ""
        if (minutes < 10) {
            minutesString = "0" + minutes
        } else {
            minutesString = "$minutes"
        }
        if (seconds < 10) {
            secondsString = "0" + seconds
        } else {
            secondsString = "$seconds"
        }

        val timerView: TextView = findViewById(R.id.timer)
        timerView.text = "$minutesString"+ ":" + "$secondsString"
    }

    private  fun resetTimer(){
        val btn:Button = findViewById(R.id.start)
        val seekBar: SeekBar = findViewById(R.id.seekBar)
        val timerView: TextView = findViewById(R.id.timer)

        timer.cancel()
        seekBar.isVisible = true
        seekBar.progress = 0
        btn.text = "start"
        timerView.text = "00:00"
        isTimerOn =false
    }



    fun toolBar() {
        val toolbar:MaterialToolbar = findViewById(R.id.materialToolbar)
        toolbar.inflateMenu(R.menu.timer_menu)
        toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener,
            androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
              val id = item!!.itemId
               if(id == R.id.action_settings){
                  val openSettings: Intent = Intent(this@MainActivity,settingsActivity::class.java)
                    startActivity(openSettings)
                    return true
                }else{
                    val openAbout: Intent = Intent(this@MainActivity,aboutActivity::class.java)
                    startActivity(openAbout)
                    return true
                }


            }
        })

    }

}



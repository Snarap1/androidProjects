package com.example.cooltimer

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
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
import java.lang.Exception
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {

   lateinit var timer:CountDownTimer
    lateinit var seekBar:SeekBar
    var  defaultInterval:Int = 0
     var isTimerOn:Boolean = false
    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolBar()
        sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(this)


        seekBar = findViewById(R.id.seekBar)
        seekBar.max = 600
        setIntervalFromSharedPreference(sharedPreferences)
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

            sharedPreferences.registerOnSharedPreferenceChangeListener(this)
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

                    val sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(applicationContext)

                    if (sharedPreferences.getBoolean("enable_sound",true)){
                        val mediaPlayer:MediaPlayer
                        val melodyName: String = sharedPreferences.getString("timer_sound","bell")!!

                        when{
                            melodyName.equals("bell")-> {
                                mediaPlayer = MediaPlayer.create(applicationContext,R.raw.bell_sound)
                                mediaPlayer.start()
                            }
                            melodyName.equals("siren")-> {
                                mediaPlayer = MediaPlayer.create(applicationContext,R.raw.siren)
                                mediaPlayer.start()
                            }
                            melodyName.equals("bip")-> {
                                mediaPlayer = MediaPlayer.create(applicationContext,R.raw.bip_sound)
                                mediaPlayer.start()
                            }
                        }

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
        setIntervalFromSharedPreference(sharedPreferences)
        btn.text = "start"
        isTimerOn =false
    }



    fun toolBar() {
        val toolbar:MaterialToolbar = findViewById(R.id.materialToolbar)
        toolbar.inflateMenu(R.menu.timer_menu)
        toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener,
            androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
            @SuppressLint("SuspiciousIndentation")
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


    private  fun setIntervalFromSharedPreference(sharedPreferences: SharedPreferences){
        val seekBar: SeekBar = findViewById(R.id.seekBar)
        val timerView: TextView = findViewById(R.id.timer)
        defaultInterval = Integer.valueOf(sharedPreferences.getString("default_interval","30"))
        val defaultIntervalMill:Long = (defaultInterval * 1000).toLong()
        updateTimer(defaultIntervalMill)
        seekBar.progress = defaultInterval

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if(p1.equals("default_interval")){
            setIntervalFromSharedPreference(sharedPreferences)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}



package com.example.simplemusicplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import org.w3c.dom.Text
import java.util.Timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    lateinit var playPauseIcon: ImageView
    lateinit var seekBar: SeekBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playPauseIcon = findViewById(R.id.pause)
        seekBar = findViewById(R.id.seekBar)
        val songName: TextView = findViewById(R.id.textView)


        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.noizemc)

        seekBar.max = mediaPlayer.duration
        val timer = Timer()
        timer.scheduleAtFixedRate( timerTask
        { run() { seekBar.progress = mediaPlayer.currentPosition }  }
            ,0,1000)


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar.progress = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                seekBar.visibility = View.VISIBLE
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                mediaPlayer.seekTo(seekBar.progress)
            }
        })
    }

    fun prevSong(view: View) {
        seekBar.progress = 0
        mediaPlayer.seekTo(seekBar.progress)

    }
    fun nextSong(view: View) {

    }
    fun pause(view: View) {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.pause()
            playPauseIcon.setImageResource(R.drawable.play_icon)

        }else{
            mediaPlayer.start()
            playPauseIcon.setImageResource(R.drawable.pause_icon)
        }
    }
}
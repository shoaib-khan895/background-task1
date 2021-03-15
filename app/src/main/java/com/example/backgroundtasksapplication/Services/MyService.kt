package com.example.backgroundtasksapplication.Services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import com.example.backgroundtasksapplication.R

class MyService : Service() {
    private lateinit var mediaPlayer : MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show()

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.file_example)
        mediaPlayer.isLooping = false
    }

    override fun onStart(intent: Intent?, startId: Int) {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()
        mediaPlayer.start()
    }

    override fun onDestroy() {
        Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show()
        mediaPlayer.stop()
    }
}
package com.example.backgroundtasksapplication.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


public class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val percentage = intent?.getIntExtra("level", 0)
        if(percentage != 0){
            //Toast.makeText(context, percentage.toString() + "%", Toast.LENGTH_LONG).show()

            val i = Intent("broadCastName")
            // Data you need to pass to activity
            // Data you need to pass to activity
            i.putExtra("message","battery is  "+percentage.toString()+"%")

            context!!.sendBroadcast(i)

        }
    }
}



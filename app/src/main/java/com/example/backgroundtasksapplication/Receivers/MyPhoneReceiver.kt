package com.example.backgroundtasksapplication.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.view.Gravity
import android.widget.Toast


class MyPhoneReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

         if(intent?.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            showToast(context,"Incoming call...");
        }
    }

    fun showToast(context : Context,message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
package com.example.backgroundtasksapplication

import android.content.*
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.backgroundtasksapplication.Receivers.BatteryReceiver
import com.example.backgroundtasksapplication.Services.BoundService
import com.example.backgroundtasksapplication.Services.MyService
import com.example.backgroundtasksapplication.Threads.Main

class MainActivity : AppCompatActivity() {

    private val batteryReceiver : BroadcastReceiver = BatteryReceiver()
    var myService: BoundService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ques 1 : Use start() join() and sleep() methods in single program to execute different threads.
        // See Threads directory
        val obj = Main()
        obj.main()


        //Ques 2 : Register receiver  for incoming calls and battery status.
        // See MyPhoneReceiver in Receivers directory
        // And BatteryReceiver in Receivers directory as well
        //First, get permission from the user
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE),1);
        }
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))


        //Ques 3 : Music Player with raw file to play song in background.
        // See MyService in Services directory
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val serviceIntent = Intent(applicationContext, MyService::class.java)
        btnStart.setOnClickListener {
            startService(Intent(applicationContext, MyService::class.java))
        }
        btnStop.setOnClickListener {
            stopService(Intent(applicationContext, MyService::class.java))
        }


        //Ques 4 : Bind local service from activity.
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)

        val tv_time = findViewById<TextView>(R.id.tv_time)
        val btn_time = findViewById<Button>(R.id.btn_time)
        btn_time.setOnClickListener {
            showTime(tv_time)
        }

    }

    private fun showTime(view: TextView) {
        val currentTime = myService?.getCurrentTime()
        view.text = currentTime.toString()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(batteryReceiver)
    }


    //Ques 4 : Bind local service from activity.
    // See BoundService in Services directory
    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }
}

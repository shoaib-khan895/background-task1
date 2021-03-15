package com.example.backgroundtasksapplication

import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.backgroundtasksapplication.Receivers.BatteryReceiver
import com.example.backgroundtasksapplication.Services.BoundService
import com.example.backgroundtasksapplication.Services.MyService
import com.example.backgroundtasksapplication.Threads.Main


class MainActivity : AppCompatActivity() {

    private var broadcastReceiver: BroadcastReceiver? = null
    private val batteryReceiver: BroadcastReceiver = BatteryReceiver()
    var myService: BoundService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val obj = Main()
        obj.main()



        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE), 1);
        }
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))



        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val serviceIntent = Intent(applicationContext, MyService::class.java)
        btnStart.setOnClickListener {
            startService(Intent(applicationContext, MyService::class.java))
        }
        btnStop.setOnClickListener {
            stopService(Intent(applicationContext, MyService::class.java))
        }



        val intent = Intent(this, BoundService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)

        val tv_time = findViewById<TextView>(R.id.tv_time)
        val btn_time = findViewById<Button>(R.id.btn_time)
        btn_time.setOnClickListener {
            showTime(tv_time)
        }

        val tvPercent = findViewById<TextView>(R.id.TV_battery)
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val b = intent.extras
                val message = b!!.getString("message")
                    tvPercent.text = message
            }
        }


    }

    override fun onResume() {
        super.onResume()
        registerReceiver(broadcastReceiver, IntentFilter("broadCastName"))
    }

    private fun showTime(view: TextView) {
        val currentTime = myService?.getCurrentTime()
        view.text = currentTime.toString()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(batteryReceiver)
    }



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

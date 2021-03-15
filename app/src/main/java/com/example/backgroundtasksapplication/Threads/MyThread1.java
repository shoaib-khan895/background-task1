package com.example.backgroundtasksapplication.Threads;

import android.util.Log;

import static java.sql.DriverManager.println;

public class MyThread1 extends Thread{
    @Override
    public void run() {
        Log.e("MyThread1 : ", "is running");
        try{
            Thread.sleep(500);
        }catch (Exception e){
            System.out.println("Exception : " + e);
        }
    }
}

package com.example.backgroundtasksapplication.Threads;

public class Main {
    public void main(){
        MyThread myThread = new MyThread();
        MyThread1 myThread1 = new MyThread1();

        myThread.start();

        try{
            myThread.join();
        }catch (Exception e){
            System.out.println("Exception : " + e);
        }

        myThread1.start();
    }
}

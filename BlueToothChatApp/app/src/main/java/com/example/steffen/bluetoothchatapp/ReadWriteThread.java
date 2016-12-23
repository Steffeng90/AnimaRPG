package com.example.steffen.bluetoothchatapp;

import android.bluetooth.BluetoothSocket;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Steffen on 20.12.2016.
 */

public class ReadWriteThread extends Thread {
    private BluetoothSocket socket;
    private InputStream inStream =null;
    private OutputStream outStream =null;
    private ReadWriteThread(BluetoothSocket socket){
        this.socket=socket;


        try{
            inStream=socket.getInputStream();
            outStream=socket.getOutputStream();
        }catch(Exception e)
        {
            Log.e("ChatApp",e.getMessage());
        }
    }
    public void run(){
        byte[] buffer=new byte[1024];
        int bytes;

        while(true){
            try{
                bytes=inStream.read(buffer);
                Log.v("ChatApp",new String(buffer,"UTF-8"));
            }catch (Exception e){
                Log.e("ChatApp",e.getMessage());
            }
        }
    }

}

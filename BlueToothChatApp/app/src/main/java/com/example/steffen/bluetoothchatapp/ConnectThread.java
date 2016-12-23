package com.example.steffen.bluetoothchatapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.util.UUID;

/**
 * Created by Steffen on 20.12.2016.
 */

public class ConnectThread extends Thread {
    private final UUID MY_UUID=UUID.fromString("ebc1822f-268e-4bdb-97b0-af1b270a12a9");

    private BluetoothSocket socket;
    public ConnectThread(BluetoothDevice device) {
        try {
            device.createRfcommSocketToServiceRecord(MY_UUID);

        } catch (Exception e) {
            Log.e("ChatAPP", e.getMessage());
        }
    }
        public void run(){
        try{
            socket.connect();
        }catch (Exception e){
            Log.e("ChatAPP", e.getMessage());

            try{
                socket.close();
            }catch(Exception ex){
                Log.e("ChatAPP", e.getMessage());

            }
        }
    }
    }


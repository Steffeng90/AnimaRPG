package com.example.steffen.bluetoothchatapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;
import java.util.UUID;

import static android.R.attr.button;
import static android.R.attr.duration;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter btAdapter;
    BluetoothDevice btDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAdapter = BluetoothAdapter.getDefaultAdapter();


        final TextView text=(TextView) findViewById(R.id.textView);
        if(btAdapter.isEnabled())
        {
            String name="\n"+btAdapter.getName()+" \n";
            String adress=btAdapter.getAddress();
            text.append(name+" "+adress+"\n");
            Set<BluetoothDevice> pairedDevices =btAdapter.getBondedDevices();
            if(pairedDevices.size()>0){
                for(BluetoothDevice device:pairedDevices){
                    text.append(device.getName()+"\n");
                }
            }
            btDevice= (BluetoothDevice) pairedDevices.toArray()[0];
        }
        else{
            Toast toast=Toast.makeText(this.getApplicationContext(),"Mach mal was richtig!",Toast.LENGTH_LONG);
            toast.show();
            Intent enabledBtIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enabledBtIntent,1);
        }
        final FloatingActionButton button=(FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                text.append("Hallo\n"+"Hallo\n");
            }

        });
        final BluetoothDevice tmpDevice=btDevice;
        final Button button2=(Button) findViewById(R.id.button7);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ConnectThread connectThread=new ConnectThread(tmpDevice);
                connectThread.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

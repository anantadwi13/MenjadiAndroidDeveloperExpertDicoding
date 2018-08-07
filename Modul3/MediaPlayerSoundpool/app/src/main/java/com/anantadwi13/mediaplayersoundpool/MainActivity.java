package com.anantadwi13.mediaplayersoundpool;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    MyReceiver receiver;
    MediaPlayerService service;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*IntentFilter intentFilter = new IntentFilter("com.anantadwi13.mediaplayersoundpool.receiver.mybroadcast");
        receiver = new MyReceiver();
        registerReceiver(receiver,intentFilter);
        sendBroadcast(new Intent("com.anantadwi13.mediaplayersoundpool.receiver.mybroadcast"));*/
        service = new MediaPlayerService(this);
        intent = new Intent(this, service.getClass());
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        super.onDestroy();
    }
}

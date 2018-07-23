package com.anantadwi13.mediaplayersoundpool;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this,MediaPlayerService.class);
        startService(intent);
        Log.d("mantap", "onCreate: "+getExternalFilesDir(null));
    }
}

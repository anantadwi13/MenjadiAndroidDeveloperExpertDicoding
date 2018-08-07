package com.anantadwi13.mediaplayersoundpool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("mantap", "onReceive: Receiver jalan");
        Intent myservice = new Intent(context,MediaPlayerService.class);
        context.startService(myservice);
    }
}

package com.anantadwi13.cataloguemovie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.anantadwi13.cataloguemovie.Notification.DailyReminderReceiver;
import com.anantadwi13.cataloguemovie.Notification.ReleaseTodayReminderReceiver;

public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DailyReminderReceiver.setNotification(context);
        ReleaseTodayReminderReceiver.setNotification(context);
    }
}

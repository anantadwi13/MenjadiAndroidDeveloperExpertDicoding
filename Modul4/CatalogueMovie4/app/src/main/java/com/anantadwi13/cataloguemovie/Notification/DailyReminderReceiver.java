package com.anantadwi13.cataloguemovie.Notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.anantadwi13.cataloguemovie.MainActivity;
import com.anantadwi13.cataloguemovie.Preferences.AppReminderPref;
import com.anantadwi13.cataloguemovie.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DailyReminderReceiver extends BroadcastReceiver {
    public static int REQ_CODE_ALARM = 1001, REQ_CODE_NOTIFICATION = 101, DAILY_NOTIFICATION_ID=1;

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
        Log.e("NOTIFICATION", "onReceive Daily: DITERIMA");
        setNotification(context);
    }

    public void showNotification(Context context){
        NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,REQ_CODE_NOTIFICATION,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.daily_reminder_text))
                .setVibrate(new long[]{200,200,200,200,200,500})
                .setAutoCancel(true);

        notifManager.notify(DAILY_NOTIFICATION_ID,builder.build());
    }

    public static void setNotification(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Calendar jam7 = Calendar.getInstance();
        jam7.set(Calendar.HOUR_OF_DAY,7);
        jam7.set(Calendar.MINUTE,0);
        jam7.set(Calendar.SECOND,0);
        jam7.set(Calendar.MILLISECOND,0);
        if (calendar.getTimeInMillis()>=jam7.getTimeInMillis())
        {
            jam7.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
        }
        Intent intent = new Intent(context,DailyReminderReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQ_CODE_ALARM,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (!new AppReminderPref(context).getDailyReminderOn()) {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
                Log.e("NOTIFICATION", "setNotification: notification canceled");
            }
        }
        else {
            if (alarmManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, jam7.getTimeInMillis(), pendingIntent);
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, jam7.getTimeInMillis(), pendingIntent);
                else
                    alarmManager.set(AlarmManager.RTC_WAKEUP, jam7.getTimeInMillis(), pendingIntent);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Log.e("NOTIFICATION", "setNotification: diset pada " + sdf.format(jam7.getTime()));
            }
        }
    }
}

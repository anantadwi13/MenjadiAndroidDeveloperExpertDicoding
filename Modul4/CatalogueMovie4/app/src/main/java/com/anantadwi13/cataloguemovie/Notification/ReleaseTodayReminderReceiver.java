package com.anantadwi13.cataloguemovie.Notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.anantadwi13.cataloguemovie.BuildConfig;
import com.anantadwi13.cataloguemovie.Database.FavFilmDB;
import com.anantadwi13.cataloguemovie.DetailActivity;
import com.anantadwi13.cataloguemovie.MainActivity;
import com.anantadwi13.cataloguemovie.Model.Film;
import com.anantadwi13.cataloguemovie.Preferences.AppReminderPref;
import com.anantadwi13.cataloguemovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class ReleaseTodayReminderReceiver extends BroadcastReceiver {
    public static int REQ_CODE_ALARM = 1002, REQ_CODE_NOTIFICATION = 102, RELEASE_NOTIFICATION_ID =2;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Context ctx = context;
        final Calendar dateNow = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Film> filmlist = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.ApiKey;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                //setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray result = jsonResponse.getJSONArray("results");
                    for (int i=0; i<result.length();i++){
                        Film film = new Film(result.getJSONObject(i));
                        if (film.getRelease_date().equals(sdf.format(dateNow.getTime())))
                            filmlist.add(film);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showNotification(ctx,filmlist);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        Log.e("NOTIFICATION", "onReceive Release: DITERIMA");
        setNotification(context);
    }

    public void showNotification(Context context,ArrayList<Film> list){
        if (list.size()==0) return;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        String RELEASE_NOTIF = "com.anantadwi13.cataloguemovie.notification.RELESE_NOTIF";

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        int x=0;
        for (Film film:list) {
            x++;
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ITEM,film);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, REQ_CODE_NOTIFICATION+x, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification=
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle(film.getTitle())
                            .setContentText(film.getTitle()+" has just been released!")
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .setGroup(RELEASE_NOTIF)
                            .build();
            notificationManager.notify(RELEASE_NOTIFICATION_ID+x, notification);
            style.addLine(film.getTitle()+" has just been released!");
        }
        style.setBigContentTitle(context.getString(R.string.app_name))
                .setSummaryText(x+" new film(s)");

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, REQ_CODE_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification summaryNotification =
                new NotificationCompat.Builder(context)
                        .setContentTitle(context.getString(R.string.app_name))
                        //set content text to support devices running API level < 24
                        .setContentText(x+" new film(s)")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        //build summary info into InboxStyle template
                        .setStyle(style)
                        //specify which group this notification belongs to
                        .setGroup(RELEASE_NOTIF)
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .setAutoCancel(true)
                        .build();

        notificationManager.notify(RELEASE_NOTIFICATION_ID, summaryNotification);
    }

    public static void setNotification(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Calendar jam8 = Calendar.getInstance();
        jam8.set(Calendar.HOUR_OF_DAY,8);
        jam8.set(Calendar.MINUTE,0);
        jam8.set(Calendar.SECOND,0);
        jam8.set(Calendar.MILLISECOND,0);
        if (calendar.getTimeInMillis()>=jam8.getTimeInMillis())
        {
            jam8.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
        }
        Intent intent = new Intent(context,ReleaseTodayReminderReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQ_CODE_ALARM,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (!new AppReminderPref(context).getReleaseReminderOn()) {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
                Log.e("NOTIFICATION", "setNotification: notification canceled");
            }
        }
        else {
            if (alarmManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, jam8.getTimeInMillis(), pendingIntent);
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, jam8.getTimeInMillis(), pendingIntent);
                else
                    alarmManager.set(AlarmManager.RTC_WAKEUP, jam8.getTimeInMillis(), pendingIntent);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Log.e("NOTIFICATION", "setNotification: diset pada " + sdf.format(jam8.getTime()));
            }
        }
    }
}

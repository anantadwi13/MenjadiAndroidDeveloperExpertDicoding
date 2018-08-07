package com.anantadwi13.mediaplayersoundpool;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MediaPlayerService extends Service {
    //MyTask task;
    Context context;

    public MediaPlayerService(Context context) {
        super();
        this.context = context;
    }

    public MediaPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        Log.e("mantap", "doInBackground: OK JALAN ");
        /*if (task==null) {
            //task = new MyTask();
            //task.execute();
        }*/
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("mantap", "onDestroy: Context kosong");

        Intent intebroad =new Intent("com.anantadwi13.oka.receiver.mybroadcast");
        sendBroadcast(intebroad);
    }
/*
    public class MyTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("mantap", "onStartCommand: Siap");
            try {
                while (true) {
                    Thread.sleep(1000);
                    //Toast.makeText(getBaseContext(), "oke", Toast.LENGTH_SHORT).show();
                    Log.e("mantap", "doInBackground: OK JALAN "+getApplicationContext().toString());*//*
                    File folder = new File("/storage/emulated/0/coba/");
                        folder.mkdirs();
                    File file = new File(folder,"/logku.txt");
                    FileOutputStream fos = new FileOutputStream(file);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    outputStreamWriter.write(sdf.format(Calendar.getInstance()));
                    outputStreamWriter.close();*//*
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
                Log.e("mantaperror", "doInBackground: "+e.toString());
            }
            return null;
        }
    }*/
}

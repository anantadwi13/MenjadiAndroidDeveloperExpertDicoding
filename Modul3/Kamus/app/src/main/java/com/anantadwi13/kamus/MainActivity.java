package com.anantadwi13.kamus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.anantadwi13.kamus.Database.KamusDB;
import com.anantadwi13.kamus.Model.Word;
import com.anantadwi13.kamus.Preference.AppSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBarImport,progressBarLoading;
    private KamusDB kamusDB;
    private boolean isFirstLaunch;
    private AppSetting appSetting;
    private static final int ENG_IND_TYPE = 100,
            IND_ENG_TYPE= 101;
    private AsyncTaskLoaderKamus task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBarImport = findViewById(R.id.progressImport);
        progressBarLoading = findViewById(R.id.progressLoading);

        Drawable progressDrawable = progressBarImport.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        progressBarLoading.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        kamusDB = new KamusDB(this);
        appSetting = new AppSetting(this);

        isFirstLaunch = appSetting.getFirstLaunch();

        if (isFirstLaunch)
            progressBarImport.setVisibility(View.VISIBLE);
        else
            progressBarLoading.setVisibility(View.VISIBLE);

        task = new AsyncTaskLoaderKamus();
        task.execute();

        NotificationCompat.Builder aaa = new NotificationCompat.Builder(this);

    }

    @Override
    protected void onDestroy() {
        Log.e(getClass().getSimpleName(), "onDestroy: DIDESTROY");
        if (task!=null)
            task.cancel(true);
        super.onDestroy();
    }

    private ArrayList<Word> loadWordsFromRaw(int type){
        ArrayList<Word> words = new ArrayList<>();
        String strLine = null;
        int id;
        switch (type){
            case IND_ENG_TYPE:
                id = R.raw.indonesia_english;
                break;
            case ENG_IND_TYPE:
                id = R.raw.english_indonesia;
                break;
            default:
                id = R.raw.english_indonesia;
        }

        try {
            InputStream inputStream = getResources().openRawResource(id);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((strLine = reader.readLine()) != null){
                String[] strings = strLine.split("\t");
                words.add(new Word(strings[0],strings[1]));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return words;
    }

    private class AsyncTaskLoaderKamus extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (isFirstLaunch) {
                ArrayList<Word> engIndWords = loadWordsFromRaw(ENG_IND_TYPE);
                ArrayList<Word> indEngWords = loadWordsFromRaw(IND_ENG_TYPE);

                boolean isSuccess = true;
                double min=0, max=80,
                        progressDiff = (max-min)/(engIndWords.size()+indEngWords.size()),
                        now=min;

                kamusDB.open();
                kamusDB.beginTransaction();

                try {
                    for (Word kata : engIndWords) {
                        kamusDB.insertTransaction(kata,KamusDB.TYPE_ENG_IND);
                        now += progressDiff;
                        publishProgress((int) now);
                    }
                    for (Word kata : indEngWords) {
                        kamusDB.insertTransaction(kata,KamusDB.TYPE_IND_ENG);
                        now += progressDiff;
                        publishProgress((int) now);
                    }
                    kamusDB.setTransactionSuccess();
                }catch (Exception e){
                    isSuccess = false;
                    e.printStackTrace();
                }

                kamusDB.endTransaction();
                kamusDB.close();
                publishProgress(100);
                if (isSuccess)
                    appSetting.setFirstLaunch(false);
                return isSuccess;
            }
            else
            {
                try {
                    synchronized (this) {
                        this.wait(500);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Log.e(getClass().getSimpleName(), "onPostExecute: SUKSES");
            Intent kamusActivity = new Intent(MainActivity.this,KamusActivity.class);
            startActivity(kamusActivity);
            finish();
        }

        @Override
        protected void onCancelled() {
            Log.e(getClass().getSimpleName(), "onCancelled: DICANCEL");
            super.onCancelled();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                progressBarImport.setProgress(values[0],true);
            else
                progressBarImport.setProgress(values[0]);
        }
    }
}

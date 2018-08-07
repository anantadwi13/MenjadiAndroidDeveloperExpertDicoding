package com.anantadwi13.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.anantadwi13.cataloguemovie.Notification.DailyReminderReceiver;
import com.anantadwi13.cataloguemovie.Notification.ReleaseTodayReminderReceiver;
import com.anantadwi13.cataloguemovie.Preferences.AppReminderPref;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearLayout, dailyReminder, releaseReminder;
    private Switch switchDailyReminder, switchReleaseReminder;
    private AppReminderPref appReminderPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = findViewById(R.id.settings_lang);
        dailyReminder = findViewById(R.id.dailyReminder);
        releaseReminder = findViewById(R.id.releaseReminder);
        switchDailyReminder = findViewById(R.id.switchDailyReminder);
        switchReleaseReminder = findViewById(R.id.switchReleaseReminder);

        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        });

        appReminderPref = new AppReminderPref(this);

        dailyReminder.setOnClickListener(this);
        releaseReminder.setOnClickListener(this);
        switchDailyReminder.setOnClickListener(this);
        switchReleaseReminder.setOnClickListener(this);

        switchDailyReminder.setChecked(appReminderPref.getDailyReminderOn());
        switchReleaseReminder.setChecked(appReminderPref.getReleaseReminderOn());
    }

    @Override
    public void onClick(View view) {
        boolean flag;
        switch (view.getId()){
            case R.id.dailyReminder:
            case R.id.switchDailyReminder:
                flag = appReminderPref.getDailyReminderOn();
                appReminderPref.setDailyReminderOn(!flag);
                switchDailyReminder.setChecked(!flag);
                DailyReminderReceiver.setNotification(this);
                break;
            case R.id.releaseReminder:
            case R.id.switchReleaseReminder:
                flag = appReminderPref.getReleaseReminderOn();
                appReminderPref.setReleaseReminderOn(!flag);
                switchReleaseReminder.setChecked(!flag);
                ReleaseTodayReminderReceiver.setNotification(this);
                break;
        }
    }
}

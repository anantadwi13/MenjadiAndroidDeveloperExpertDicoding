package com.anantadwi13.mywidget;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button start,stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.btn_start);
        stop = findViewById(R.id.btn_stop);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                startJob();
                break;
            case R.id.btn_stop:
                stopJob();
                break;
        }
    }

    private static int JOB_ID = 100, PERIOD_SCHEDULER = 10000;

    private void startJob(){
        ComponentName serviceComponent = new ComponentName(this,UpdateWidgetService.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,serviceComponent);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
            builder.setPeriodic(PERIOD_SCHEDULER);
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());

            Toast.makeText(this, "JOB CREATED", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopJob(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.cancel(JOB_ID);
            Toast.makeText(this, "JOB CANCELED", Toast.LENGTH_SHORT).show();
        }

    }
}

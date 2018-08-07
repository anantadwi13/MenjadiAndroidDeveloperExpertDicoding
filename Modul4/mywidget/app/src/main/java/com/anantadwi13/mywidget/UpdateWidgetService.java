package com.anantadwi13.mywidget;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class UpdateWidgetService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews view = new RemoteViews(getPackageName(),R.layout.coba_widget);
        ComponentName widget = new ComponentName(this,CobaWidget.class);

        String text = "Random: "+RandomGenerator.Generate(100);

        view.setTextViewText(R.id.appwidget_text,text);
        appWidgetManager.updateAppWidget(widget,view);

        Log.e("MYJOB", "onStartJob: OKE");

        jobFinished(jobParameters,false);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}

package com.anantadwi13.mywidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class CobaWidget extends AppWidgetProvider {

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        String number = "Random : "+RandomGenerator.Generate(100);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.coba_widget);
        views.setTextViewText(R.id.appwidget_text, number);
        views.setOnClickPendingIntent(R.id.appwidget_text,getPendingIntent(context,appWidgetId,WIDGET_CLICK));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String WIDGET_CLICK = "widgetsclick", WIDGET_ID_EXTRA = "widgetidextra";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction()!=null && intent.getAction().equals(WIDGET_CLICK)){
            AppWidgetManager manager = AppWidgetManager.getInstance(context);

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.coba_widget);

            String text = "Random: "+RandomGenerator.Generate(100);

            int appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA,0);

            views.setTextViewText(R.id.appwidget_text,text);
            manager.updateAppWidget(appWidgetId,views);
        }
    }

    private PendingIntent getPendingIntent(Context context,int appWidgetId, String action){
        Intent intent = new Intent(context,getClass());
        intent.setAction(action);
        intent.putExtra(WIDGET_ID_EXTRA,appWidgetId);
        return PendingIntent.getBroadcast(context,appWidgetId,intent,0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


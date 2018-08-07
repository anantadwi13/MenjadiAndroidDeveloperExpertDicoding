package com.anantadwi13.cataloguemovie.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.anantadwi13.cataloguemovie.Adapter.CatalogueAdapterCursor;
import com.anantadwi13.cataloguemovie.Database.FavFilmDB;
import com.anantadwi13.cataloguemovie.DetailActivity;
import com.anantadwi13.cataloguemovie.Model.Film;
import com.anantadwi13.cataloguemovie.R;

import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.CONTENT_URI;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    public static final String ACTION_CLICK = "com.anantadwi13.cataloguemovie.widget.ACTION_CLICK";
    public static final String EXTRA_ITEM = "com.anantadwi13.cataloguemovie.widget.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context,StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setRemoteAdapter(R.id.stackview,intent);
        views.setEmptyView(R.id.stackview,R.id.empty);

        Intent clickIntent = new Intent(context,NewAppWidget.class);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        clickIntent.setAction(ACTION_CLICK);
        clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingClickIntent = PendingIntent.getBroadcast(context,0,clickIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stackview,pendingClickIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(ACTION_CLICK)){
            int id = intent.getIntExtra(EXTRA_ITEM, 0);

            Cursor data = context.getContentResolver().query(Uri.parse(CONTENT_URI+"/"+id),null,null,null,null);
            if (data!=null && data.moveToFirst()) {
                Intent detail = new Intent(context, DetailActivity.class);
                detail.putExtra(DetailActivity.EXTRA_ITEM,new Film(data));
                detail.putExtra(DetailActivity.EXTRA_FROM_WIDGET,true);
                detail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(detail);
            }
        }
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            int[] ids = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            mgr.notifyAppWidgetViewDataChanged(ids,R.id.stackview);
        }
        super.onReceive(context, intent);
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


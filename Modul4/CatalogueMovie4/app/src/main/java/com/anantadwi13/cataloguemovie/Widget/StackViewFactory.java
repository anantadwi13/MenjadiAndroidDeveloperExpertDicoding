package com.anantadwi13.cataloguemovie.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.anantadwi13.cataloguemovie.Database.FavFilmDB;
import com.anantadwi13.cataloguemovie.Model.Film;
import com.anantadwi13.cataloguemovie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Film> list = new ArrayList<>();
    private Context context;
    private int widgetID;

    public StackViewFactory(Context context, Intent intent) {
        this.context = context;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        FavFilmDB db = new FavFilmDB().open(context);
        list = db.getAllFilm();
        db.close();
    }

    @Override
    public void onDestroy() {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.e("WIDGET", "getViewAt widget "+widgetID+": size"+list.size());
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        if (list.get(i)!=null && list.size()>0) {
            Log.e("WIDGET", "OK SIP pos:"+i);
            Bitmap bmp = null;
            try {
                bmp = Glide.with(context).asBitmap()
                        .load(list.get(i).getPosterURL())
                        .apply(new RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            views.setImageViewBitmap(R.id.image, bmp);
            views.setTextViewText(R.id.date, list.get(i).getRelease_date());

            Bundle extras = new Bundle();
            extras.putInt(NewAppWidget.EXTRA_ITEM, list.get(i).getId());
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);

            views.setOnClickFillInIntent(R.id.widget_item_container, fillInIntent);
        }
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

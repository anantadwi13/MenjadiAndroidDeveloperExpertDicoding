package com.anantadwi13.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AsyncTaskLoaderku extends AsyncTaskLoader<ArrayList<WeatherItems>> {
    private ArrayList<WeatherItems> mData;
    private Boolean mHasResult = false;

    private String kumpulanKota;
    private static String API_KEY = "732eeca9f92182d34315b5ea78fe96da";


    public AsyncTaskLoaderku(Context context, String kumpulanKota) {
        super(context);
        onContentChanged();
        this.kumpulanKota = kumpulanKota;
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(ArrayList<WeatherItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            //ReleaseResource
            mData = null;
            mHasResult = false;
        }
    }



    @Override
    public ArrayList<WeatherItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<WeatherItems> weatherItems = new ArrayList<>();

        String url = "http://api.openweathermap.org/data/2.5/group?id=" +
                kumpulanKota+ "&units=metric&appid=" + API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonResult = new JSONObject(result);
                    JSONArray list = jsonResult.getJSONArray("list");
                    for (int i =0;i<list.length();i++){
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItems weatherItem = new WeatherItems(weather);
                        weatherItems.add(weatherItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return weatherItems;
    }
}

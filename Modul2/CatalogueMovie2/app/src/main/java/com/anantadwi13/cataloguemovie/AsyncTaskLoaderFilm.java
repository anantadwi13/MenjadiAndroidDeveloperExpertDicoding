package com.anantadwi13.cataloguemovie;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class AsyncTaskLoaderFilm extends AsyncTaskLoader<ArrayList<Film>>{
    private ArrayList<Film> list;
    private boolean hasResult = false;
    private String url;

    public AsyncTaskLoaderFilm(Context context, String url) {
        super(context);

        this.url = url;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(list);
    }

    @Override
    public void deliverResult(ArrayList<Film> data) {
        list = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult){
            list = null;
            hasResult = false;
        }
    }

    @Override
    public ArrayList<Film> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Film> filmlist = new ArrayList<>();
        //String urlSearch = "https://api.themoviedb.org/3/search/movie?api_key="+BuildConfig.ApiKey+"&language="+language+"&query="+judulFilm;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray result = jsonResponse.getJSONArray("results");
                    for (int i=0; i<result.length();i++){
                        filmlist.add(new Film(result.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return filmlist;
    }
}

package com.anantadwi13.cataloguemovie;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {
    private ArrayList<MovieItem> movieItems;
    private boolean hasResult = false;
    private String judulFilm;
    private static String API_KEY = BuildConfig.ApiKey;

    public MovieAsyncTaskLoader(Context context, String judulFilm) {
        super(context);

        onContentChanged();
        this.judulFilm = judulFilm;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(movieItems);
    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        movieItems = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            //release resources
            movieItems = null;
            hasResult = false;
        }
    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<MovieItem> movieList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+judulFilm;
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
                        movieList.add(new MovieItem(result.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieList;
    }
}

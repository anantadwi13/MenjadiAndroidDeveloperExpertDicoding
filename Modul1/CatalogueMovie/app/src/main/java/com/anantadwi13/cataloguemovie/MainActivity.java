package com.anantadwi13.cataloguemovie;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>, View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText etJudulFilm;
    private Button btnCari;
    private ListView listView;
    private CatalogueAdapter adapter;
    private ProgressBar progressBar;
    private static String EXTRA_JUDUL = "extra_judul";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etJudulFilm = findViewById(R.id.judulFilm);
        btnCari = findViewById(R.id.btn_cari);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        adapter = new CatalogueAdapter(this);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(this);

        btnCari.setOnClickListener(this);

        listView.setAdapter(adapter);

        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, Bundle bundle) {
        String judulFilm = "";
        if (bundle!=null)
            judulFilm = bundle.getString(EXTRA_JUDUL);

        return new MovieAsyncTaskLoader(this, judulFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> movieItems) {
        adapter.setMovieItems(movieItems);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        adapter.setMovieItems(null);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        String judulFilm = etJudulFilm.getText().toString().trim();
        if (!TextUtils.isEmpty(judulFilm)){
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_JUDUL,judulFilm);
            listView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(0,bundle,this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MovieItem movie = adapter.getItem(i);
        Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_MOVIE,movie);
        startActivity(detailIntent);
    }
}

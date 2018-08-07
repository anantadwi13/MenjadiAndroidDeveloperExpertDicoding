package com.anantadwi13.cataloguemovie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anantadwi13.cataloguemovie.Database.FavFilmDB;
import com.anantadwi13.cataloguemovie.Halaman.FavFragment;
import com.anantadwi13.cataloguemovie.Model.Film;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.CONTENT_URI;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.FILM_ID;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.ORIGINAL_LANG;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.OVERVIEW;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.POSTER_URL;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.RELEASE_DATE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.TITLE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_AVERAGE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_COUNT;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_ITEM = "extra_item", EXTRA_REMOVED ="extra_changed";
    private TextView detailTitle,detailOverview,detailReleaseDate,detailLanguage,detailRating;
    private ImageView detailPoster,fav;
    private Film film;
    private boolean isRemoved =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        detailTitle = findViewById(R.id.detailTitle);
        detailOverview = findViewById(R.id.detailOverview);
        detailReleaseDate = findViewById(R.id.detailReleaseDate);
        detailLanguage = findViewById(R.id.detailLanguage);
        detailRating = findViewById(R.id.detailRating);
        detailPoster = findViewById(R.id.detailPoster);
        fav = findViewById(R.id.fav);
        fav.setOnClickListener(this);

        if (getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        film = getIntent().getParcelableExtra(EXTRA_ITEM);
        if (film!=null){
            getSupportActionBar().setTitle(film.getTitle());
            detailTitle.setText(film.getTitle());
            detailOverview.setText(film.getOverview());
            detailLanguage.setText(film.getOriginal_language());
            detailRating.setText(String.valueOf(film.getVote_average()));

            Glide.with(this).load(film.getPosterURL())
                    .apply(new RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                    .into(detailPoster);

            checkFav();
            try {
                String[] date = film.getRelease_date().split("-");
                int tahun = Integer.valueOf(date[0]);
                int bulan = Integer.valueOf(date[1]) - 1;
                int tanggal = Integer.valueOf(date[2]);

                Calendar calendar = Calendar.getInstance();
                calendar.set(tahun, bulan, tanggal);

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
                detailReleaseDate.setText(dateFormat.format(calendar.getTime()));
            }
            catch (Exception e) {
                detailReleaseDate.setText("-");
            }
        }
    }

    private void checkFav(){
        if (film==null) return;
        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI+"/"+film.getId()),null,null,null,null);
        if (cursor!=null && cursor.getCount()>0) {
            fav.setImageResource(R.drawable.ic_round_star);
        }
        else
            fav.setImageResource(R.drawable.ic_round_star_border);
        if (cursor!=null)
            cursor.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (isRemoved){
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_REMOVED, isRemoved);
            setResult(FavFragment.REQ_CODE,resultIntent);
            finish();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean insert(Film film){
        if (film==null) return false;
        ContentValues field = new ContentValues();
        field.put(FILM_ID,film.getId());
        field.put(VOTE_COUNT,film.getVote_count());
        field.put(VOTE_AVERAGE,film.getVote_average());
        field.put(TITLE,film.getTitle());
        field.put(OVERVIEW,film.getOverview());
        field.put(RELEASE_DATE,film.getRelease_date());
        field.put(POSTER_URL,film.getPosterURL());
        field.put(ORIGINAL_LANG, film.getOriginal_language());
        return getContentResolver().insert(CONTENT_URI,field)==null;
    }

    private boolean delete(int film_id){
        return getContentResolver().delete(Uri.parse(CONTENT_URI+"/"+film_id),null,null)>0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fav:
                if (film==null) return;
                Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI+"/"+film.getId()),null,null,null,null);
                if (cursor!=null && cursor.getCount()>0) {
                    delete(film.getId());
                    isRemoved = true;
                }
                else {
                    insert(film);
                    isRemoved=false;
                }
                if (cursor!=null)
                    cursor.close();
                checkFav();
                break;
        }
    }
}

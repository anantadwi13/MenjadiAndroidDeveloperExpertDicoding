package com.anantadwi13.cataloguemovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
    public static String EXTRA_ITEM = "extra_item";
    private TextView detailTitle,detailOverview,detailReleaseDate,detailLanguage,detailRating;
    private ImageView detailPoster;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Film film = getIntent().getParcelableExtra(EXTRA_ITEM);
        if (film!=null){
            getSupportActionBar().setTitle(film.getTitle());
            detailTitle.setText(film.getTitle());
            detailOverview.setText(film.getOverview());
            detailLanguage.setText(film.getOriginal_language());
            detailRating.setText(String.valueOf(film.getVote_average()));

            Glide.with(this).load(film.getPosterURL())
                    .apply(new RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                    .into(detailPoster);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

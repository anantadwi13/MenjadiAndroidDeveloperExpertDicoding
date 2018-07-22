package com.anantadwi13.cataloguemovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
    public static String EXTRA_MOVIE = "extra_movie";
    private TextView detailTitle,detailOverview,detailReleaseDate,detailLanguage,detailRating;
    private ImageView detailPoster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailTitle = findViewById(R.id.detailTitle);
        detailOverview = findViewById(R.id.detailOverview);
        detailReleaseDate = findViewById(R.id.detailReleaseDate);
        detailLanguage = findViewById(R.id.detailLanguage);
        detailRating = findViewById(R.id.detailRating);
        detailPoster = findViewById(R.id.detailPoster);


        MovieItem movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        detailTitle.setText(movie.getTitle());
        detailOverview.setText(movie.getOverview());
        detailLanguage.setText(movie.getOriginal_language());
        detailRating.setText(String.valueOf(movie.getVote_average()));

        Glide.with(this).load(movie.getPosterURL())
                .apply(new RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                .into(detailPoster);

        try {
            String[] date = movie.getRelease_date().split("-");
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

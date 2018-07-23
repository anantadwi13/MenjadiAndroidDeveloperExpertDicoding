package com.anantadwi13.kamus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.anantadwi13.kamus.Model.Word;

public class DetailActivity extends AppCompatActivity {
    public static String EXTRA = "extra_word";
    private TextView tvKata,tvTerjemahan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvKata = findViewById(R.id.kata);
        tvTerjemahan = findViewById(R.id.terjemahan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Word kata = getIntent().getParcelableExtra(EXTRA);
        if (kata!=null){
            tvKata.setText(kata.getKata());
            tvTerjemahan.setText(kata.getTerjemahan());
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

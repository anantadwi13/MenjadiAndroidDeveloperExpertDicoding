package com.anantadwi13.asynctaskloader;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<WeatherItems>>, View.OnClickListener {
    private ListView listView;
    private WeatherAdapter weatherAdapter;
    private EditText editKota;
    private Button btnCari;

    public static String EXTRAS_CITY = "extras_city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherAdapter = new WeatherAdapter(this);
        weatherAdapter.notifyDataSetChanged();
        listView = findViewById(R.id.listView);
        listView.setAdapter(weatherAdapter);

        editKota = findViewById(R.id.edit_kota);
        btnCari = findViewById(R.id.btn_kota);

        btnCari.setOnClickListener(this);

        String kota = editKota.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CITY, kota);

        getLoaderManager().initLoader(0,bundle,this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<WeatherItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String kumpulanKota = "";
        if (args!=null)
            kumpulanKota = args.getString(EXTRAS_CITY);

        return new AsyncTaskLoaderku(this,kumpulanKota);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherItems>> loader, ArrayList<WeatherItems> weatherItems) {
        weatherAdapter.setData(weatherItems);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeatherItems>> loader) {
        weatherAdapter.setData(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_kota:
                String kota = editKota.getText().toString();

                if (!TextUtils.isEmpty(kota)){
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_CITY,kota);
                    getLoaderManager().restartLoader(0,bundle,this);
                }
                break;
        }
    }
}

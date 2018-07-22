package com.anantadwi13.cataloguemovie.halaman;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.anantadwi13.cataloguemovie.AsyncTaskLoaderFilm;
import com.anantadwi13.cataloguemovie.BuildConfig;
import com.anantadwi13.cataloguemovie.CatalogueAdapter;
import com.anantadwi13.cataloguemovie.DetailActivity;
import com.anantadwi13.cataloguemovie.Film;
import com.anantadwi13.cataloguemovie.ItemFilmClickSupport;
import com.anantadwi13.cataloguemovie.MainActivity;
import com.anantadwi13.cataloguemovie.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Film>> {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CatalogueAdapter adapter;
    private static String EXTRA_LOCALE = "extra_localeNowPlaying";
    private String locale;

    public NowPlayingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.progressbar);
        adapter = new CatalogueAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ItemFilmClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemFilmClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView rv, int position, View v) {
                Intent detail = new Intent(getContext(), DetailActivity.class);
                detail.putExtra(DetailActivity.EXTRA_ITEM,((CatalogueAdapter)rv.getAdapter()).getItem(position));
                startActivity(detail);
            }
        });

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        locale = Locale.getDefault().toString();

        getActivity().getLoaderManager().initLoader(0, null, this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String lastlocale = getActivity().getIntent().getStringExtra(EXTRA_LOCALE);
        locale = Locale.getDefault().toString();
        Log.d("NowPlaying", "onResume: LastLocale:"+lastlocale+" NewLocale:"+locale);
        if (lastlocale!=null && !lastlocale.equals(locale)) {
            getActivity().getLoaderManager().restartLoader(0, null, this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getIntent().putExtra(EXTRA_LOCALE,locale);
    }

    @Override
    public Loader<ArrayList<Film>> onCreateLoader(int i, Bundle bundle) {
        String language = "en-US";
        if (Locale.getDefault().toString().equals("in_ID"))
            language = "id-ID";
        return new AsyncTaskLoaderFilm(getContext(),"https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.ApiKey+"&language="+language);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Film>> loader, ArrayList<Film> films) {
        adapter.setList(films);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Film>> loader) {
        adapter.setList(null);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}

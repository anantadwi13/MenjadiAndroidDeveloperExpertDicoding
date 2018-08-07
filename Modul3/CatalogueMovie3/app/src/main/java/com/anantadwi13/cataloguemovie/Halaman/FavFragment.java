package com.anantadwi13.cataloguemovie.Halaman;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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

import com.anantadwi13.cataloguemovie.Adapter.CatalogueAdapter;
import com.anantadwi13.cataloguemovie.Adapter.CatalogueAdapterCursor;
import com.anantadwi13.cataloguemovie.Database.FavFilmDB;
import com.anantadwi13.cataloguemovie.DetailActivity;
import com.anantadwi13.cataloguemovie.Helper.ItemFilmClickSupport;
import com.anantadwi13.cataloguemovie.Model.Film;
import com.anantadwi13.cataloguemovie.R;

import java.util.ArrayList;
import java.util.Locale;

import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.CONTENT_URI;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.FILM_ID;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.ORIGINAL_LANG;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.OVERVIEW;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.POSTER_URL;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.RELEASE_DATE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.TITLE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_AVERAGE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_COUNT;

public class FavFragment extends Fragment{
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CatalogueAdapterCursor adapter;
    public static int REQ_CODE = 1010;
    private AsyncTaskFavFilm task;
    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.progressbar);
        adapter = new CatalogueAdapterCursor(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ItemFilmClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemFilmClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView rv, int position, View v) {
                Intent detail = new Intent(getContext(), DetailActivity.class);
                detail.putExtra(DetailActivity.EXTRA_ITEM,((CatalogueAdapterCursor)rv.getAdapter()).getItem(position));
                startActivityForResult(detail,REQ_CODE);
            }
        });

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        task = new AsyncTaskFavFilm();
        task.execute();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE)
            if (resultCode==REQ_CODE){
                boolean removed = data.getBooleanExtra(DetailActivity.EXTRA_REMOVED,false);
                if (removed) {
                    task = new AsyncTaskFavFilm();
                    task.execute();
                }
            }
    }

    @Override
    public void onDestroy() {
        if (task!=null)
            task.cancel(true);
        super.onDestroy();
        if (cursor!=null)
            cursor.close();
    }

    class AsyncTaskFavFilm extends AsyncTask<Void,Void,Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor films) {
            cursor = films;
            adapter.swapCursor(films);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    /*class AsyncTaskFavFilm extends AsyncTask<Void,Void,ArrayList<Film>> {

        @Override
        protected ArrayList<Film> doInBackground(Void... voids) {
            ArrayList<Film> filmlist;

            FavFilmDB db = new FavFilmDB().open(getContext());

            filmlist = db.getAllFilm();

            db.close();
            return filmlist;
        }

        @Override
        protected void onPostExecute(ArrayList<Film> films) {
            adapter.setList(films);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }*/
}

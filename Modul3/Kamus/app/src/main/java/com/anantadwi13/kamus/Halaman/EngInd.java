package com.anantadwi13.kamus.Halaman;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anantadwi13.kamus.Adapter.KamusAdapter;
import com.anantadwi13.kamus.Database.KamusDB;
import com.anantadwi13.kamus.Model.Word;
import com.anantadwi13.kamus.R;
import com.anantadwi13.kamus.Task.KamusAsyncTaskLoader;

import java.util.ArrayList;

public class EngInd extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Word>>, TextWatcher, View.OnClickListener {
    private EditText etSearch;
    private RecyclerView recyclerView;
    private KamusAdapter adapter;
    private static String EXTRA_CARI = "extra_cari_engind";
    private ImageView btnClear;

    public EngInd() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_halaman,container,false);
        etSearch = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recycler);
        btnClear = view.findViewById(R.id.btnClear);

        adapter = new KamusAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        btnClear.setOnClickListener(this);
        etSearch.setText(getActivity().getIntent().getStringExtra(EXTRA_CARI));
        etSearch.setSelection(etSearch.getText().length());
        etSearch.addTextChangedListener(this);

        getActivity().getLoaderManager().initLoader(0,null,this);

        return view;
    }

    @Override
    public void onDestroy() {
        getActivity().getIntent().putExtra(EXTRA_CARI,etSearch.getText().toString());
        etSearch.removeTextChangedListener(this);
        super.onDestroy();
    }

    @Override
    public Loader<ArrayList<Word>> onCreateLoader(int i, Bundle bundle) {
        String cari = null;
        if (bundle!=null)
            cari = bundle.getString(EXTRA_CARI,null);
        return new KamusAsyncTaskLoader(getContext(),KamusAsyncTaskLoader.TYPE_ENG_IND,cari);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Word>> loader, ArrayList<Word> words) {
        adapter.setList(words);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Word>> loader) {
        adapter.setList(null);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Bundle bundle = new Bundle();
        if (charSequence.length()!=0)
            bundle.putString(EXTRA_CARI,charSequence.toString());
        getActivity().getLoaderManager().restartLoader(0,bundle,this);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnClear:
                if (etSearch.getText().length()>0)
                    TextKeyListener.clear(etSearch.getText());
                //getActivity().getLoaderManager().restartLoader(0,null,this);
                break;
        }
    }
}

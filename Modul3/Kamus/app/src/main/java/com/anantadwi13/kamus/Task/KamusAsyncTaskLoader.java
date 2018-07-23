package com.anantadwi13.kamus.Task;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;

import com.anantadwi13.kamus.Database.KamusDB;
import com.anantadwi13.kamus.Model.Word;

import java.util.ArrayList;

public class KamusAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Word>> {
    private ArrayList<Word> list;
    private boolean hasResult = false;
    private Context context;
    public static final int TYPE_ENG_IND = 1, TYPE_IND_ENG = 2;
    private int TYPE;
    private String cari;

    public KamusAsyncTaskLoader(Context context, int TYPE, @Nullable String cari) {
        super(context);

        switch (TYPE){
            case TYPE_ENG_IND:
                this.TYPE = KamusDB.TYPE_ENG_IND;
                break;
            case TYPE_IND_ENG:
                this.TYPE = KamusDB.TYPE_IND_ENG;
                break;
            default:
                this.TYPE = KamusDB.TYPE_ENG_IND;
        }

        this.cari = cari;
        this.context = context;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(list);
    }

    @Override
    public void deliverResult(ArrayList<Word> data) {
        list = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult){
            list = null;
            hasResult = false;
        }
    }

    @Override
    public ArrayList<Word> loadInBackground() {
        KamusDB db = new KamusDB(context,TYPE);
        ArrayList<Word> words = new ArrayList<>();
        db.open();
        if (cari!=null)
            words = db.getWords(cari,100,null);
        db.close();
        return words;
    }
}

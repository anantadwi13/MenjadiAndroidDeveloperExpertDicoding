package com.anantadwi13.cataloguemovie.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.anantadwi13.cataloguemovie.Database.DatabaseContract;
import com.anantadwi13.cataloguemovie.Database.FavFilmDB;

import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.CONTENT_URI;

public class FavFilmProvider extends ContentProvider {
    private final static int FILM = 1,FILM_ID = 2;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavFilmDB db;

    static {
        uriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.TABLE_FAV_FILM, FILM);
        uriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.TABLE_FAV_FILM+"/#", FILM_ID);
    }

    @Override
    public boolean onCreate() {
        db = new FavFilmDB().open(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case FILM:
                cursor = db.getAllFilmProvider();
                break;
            case FILM_ID:
                cursor = db.getFilmByFilmIDProvider(uri.getLastPathSegment());
                break;
        }
        if (cursor!=null && getContext()!=null)
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id=0;
        switch (uriMatcher.match(uri)){
            case FILM:
                id = db.insertProvider(contentValues);
                break;
        }
        if (id<=0) return null;
        if (id>0 && getContext()!=null)
            getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse(CONTENT_URI+"/"+id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case FILM_ID:
                count = db.deleteFilmProvider(uri.getLastPathSegment());
                break;
        }
        if (count>0 && getContext()!=null)
            getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

package com.anantadwi13.cataloguemovie.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.FILM_ID;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.ORIGINAL_LANG;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.OVERVIEW;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.POSTER_URL;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.RELEASE_DATE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.TITLE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_AVERAGE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_COUNT;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.TABLE_FAV_FILM;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "cataloguemovie";
    private static int DB_VERSION = 1;

    private static String CREATE_TABLE_FAV_FILM = "create table "+ TABLE_FAV_FILM +
            " ("+_ID+" integer primary key autoincrement," +
            FILM_ID+" int not null," +
            VOTE_COUNT+" int," +
            VOTE_AVERAGE+" double," +
            TITLE+" text," +
            OVERVIEW+" text," +
            RELEASE_DATE+" text," +
            POSTER_URL+" text," +
            ORIGINAL_LANG+" text)";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FAV_FILM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_FAV_FILM);
        onCreate(sqLiteDatabase);
    }
}

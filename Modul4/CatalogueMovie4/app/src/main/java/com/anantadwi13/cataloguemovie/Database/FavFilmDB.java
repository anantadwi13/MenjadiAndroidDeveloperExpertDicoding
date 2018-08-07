package com.anantadwi13.cataloguemovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.anantadwi13.cataloguemovie.Model.Film;

import java.util.ArrayList;

import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.FILM_ID;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.ORIGINAL_LANG;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.OVERVIEW;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.POSTER_URL;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.RELEASE_DATE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.TITLE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_AVERAGE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_COUNT;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.TABLE_FAV_FILM;

public class FavFilmDB {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public FavFilmDB open(Context context){
        if (dbHelper==null || db==null) {
            dbHelper = new DatabaseHelper(context);
            db = dbHelper.getWritableDatabase();
        }
        return this;
    }

    public void close(){
        if (db!=null) {
            db.close();
            db = null;
        }
        if (dbHelper!=null){
            dbHelper.close();
            dbHelper = null;
        }
    }

    public long insert(Film film){
        if (film==null) return -1;
        if (db==null || !db.isOpen()) throw new SQLException("Open Database First!");

        ContentValues field = new ContentValues();
        field.put(FILM_ID,film.getId());
        field.put(VOTE_COUNT,film.getVote_count());
        field.put(VOTE_AVERAGE,film.getVote_average());
        field.put(TITLE,film.getTitle());
        field.put(OVERVIEW,film.getOverview());
        field.put(RELEASE_DATE,film.getRelease_date());
        field.put(POSTER_URL,film.getPosterURL());
        field.put(ORIGINAL_LANG, film.getOriginal_language());

        return db.insert(TABLE_FAV_FILM, null, field);
    }
    public long insertProvider(ContentValues field){
        if (field==null) return -1;
        if (db==null || !db.isOpen()) throw new SQLException("Open Database First!");

        return db.insert(TABLE_FAV_FILM, null, field);
    }

    public boolean isFavourite(int film_id){
        if (db==null || !db.isOpen()) throw new SQLException("Open Database First!");
        Cursor cursor = db.query(TABLE_FAV_FILM,new String[]{FILM_ID},FILM_ID+" = ?",new String[]{String.valueOf(film_id)},null,null,null);
        int total = cursor.getCount();
        cursor.close();
        return total>0;
    }

    public Film getFilmByFilmID(int film_id){
        if (db==null || !db.isOpen()) throw new SQLException("Open Database First!");

        Film film = new Film();

        Cursor cursor = db.query(TABLE_FAV_FILM,null,FILM_ID+" = ?",new String[]{String.valueOf(film_id)},null,null,null);

        if(cursor.moveToNext()) {
            film.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FILM_ID)));
            film.setVote_count(cursor.getInt(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
            film.setVote_average(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
            film.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
            film.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
            film.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
            film.setPosterURL(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_URL)));
            film.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANG)));
        }
        cursor.close();
        return film;
    }

    public Cursor getFilmByFilmIDProvider(String film_id){
        if (db==null || !db.isOpen()) throw new SQLException("Open Database First!");

        return db.query(TABLE_FAV_FILM,null,FILM_ID+" = ?",new String[]{film_id},null,null,null);
    }

    public ArrayList<Film> getAllFilm(){
        if (db==null || !db.isOpen()) throw new SQLException("Open Database First!");

        ArrayList<Film> list = new ArrayList<>();

        Cursor cursor = db.query(TABLE_FAV_FILM,null,null,null,null,null,null);

        while (cursor.moveToNext()) {
            Film film = new Film();
            film.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FILM_ID)));
            film.setVote_count(cursor.getInt(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
            film.setVote_average(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
            film.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
            film.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
            film.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
            film.setPosterURL(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_URL)));
            film.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANG)));
            list.add(film);
        }
        cursor.close();
        return list;
    }
    public Cursor getAllFilmProvider(){
        if (db==null || !db.isOpen()) throw new SQLException("Open Database First!");


        return db.query(TABLE_FAV_FILM,null,null,null,null,null,null);
    }

    public boolean deleteFilm(int film_id){
        if (db==null || !db.isOpen()) throw new SQLException("Open Database First!");

        return db.delete(TABLE_FAV_FILM,FILM_ID+" = ?",new String[]{String.valueOf(film_id)})>0;
    }
    public int deleteFilmProvider(String film_id){
        if (db==null || !db.isOpen()) throw new SQLException("Open Database First!");

        return db.delete(TABLE_FAV_FILM,FILM_ID+" = ?",new String[]{film_id});
    }
}

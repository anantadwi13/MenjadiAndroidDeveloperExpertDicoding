package com.anantadwi13.cataloguemovie.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.FILM_ID;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.ORIGINAL_LANG;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.OVERVIEW;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.POSTER_URL;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.RELEASE_DATE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.TITLE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_AVERAGE;
import static com.anantadwi13.cataloguemovie.Database.DatabaseContract.FavFilmColumns.VOTE_COUNT;

public class Film implements Parcelable {
    private int id,vote_count;
    private double vote_average;
    private String title, overview, release_date, posterURL, original_language;

    public Film(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");
            double vote_average = object.getDouble("vote_average");
            int vote_count = object.getInt("vote_count");
            String original_language = object.getString("original_language");
            String poster_url = object.getString("poster_path");
            poster_url = "https://image.tmdb.org/t/p/w154"+poster_url;

            setId(id);
            setTitle(title);
            setOverview(overview);
            setRelease_date(release_date);
            setPosterURL(poster_url);
            setVote_average(vote_average);
            setVote_count(vote_count);
            setOriginal_language(original_language);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Film(Cursor cursor){
        setId(cursor.getInt(cursor.getColumnIndexOrThrow(FILM_ID)));
        setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANG)));
        setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
        setPosterURL(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_URL)));
        setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
        setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
        setVote_average(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
        setVote_count(cursor.getInt(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
    }

    public Film() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.vote_count);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.posterURL);
        dest.writeString(this.original_language);
    }

    protected Film(Parcel in) {
        this.id = in.readInt();
        this.vote_count = in.readInt();
        this.vote_average = in.readDouble();
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.posterURL = in.readString();
        this.original_language = in.readString();
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}

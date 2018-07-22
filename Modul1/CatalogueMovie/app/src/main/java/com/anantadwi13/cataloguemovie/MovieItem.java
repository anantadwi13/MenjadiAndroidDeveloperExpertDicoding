package com.anantadwi13.cataloguemovie;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieItem implements Parcelable {
    private int id,vote_count;
    private double vote_average;
    private String title, overview, release_date, posterURL, original_language;

    public MovieItem(JSONObject object) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setPosterURL(String poster) {
        this.posterURL = poster;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
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

    protected MovieItem(Parcel in) {
        this.id = in.readInt();
        this.vote_count = in.readInt();
        this.vote_average = in.readDouble();
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.posterURL = in.readString();
        this.original_language = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}

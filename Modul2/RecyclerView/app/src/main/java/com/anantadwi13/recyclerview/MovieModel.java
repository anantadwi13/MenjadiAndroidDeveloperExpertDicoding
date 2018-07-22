package com.anantadwi13.recyclerview;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    private int ID;
    private String judul, releaseDate, description;

    public MovieModel() {
    }

    public MovieModel(int ID, String judul, String releaseDate, String description) {
        this.ID = ID;
        this.judul = judul;
        this.releaseDate = releaseDate;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.judul);
        dest.writeString(this.releaseDate);
        dest.writeString(this.description);
    }

    protected MovieModel(Parcel in) {
        this.ID = in.readInt();
        this.judul = in.readString();
        this.releaseDate = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}

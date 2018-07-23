package com.anantadwi13.kamus.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {
    private int id;
    private String kata,terjemahan;

    public Word(String kata, String terjemahan) {
        setKata(kata);
        setTerjemahan(terjemahan);
    }

    public Word() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getTerjemahan() {
        return terjemahan;
    }

    public void setTerjemahan(String terjemahan) {
        this.terjemahan = terjemahan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.kata);
        dest.writeString(this.terjemahan);
    }

    protected Word(Parcel in) {
        this.id = in.readInt();
        this.kata = in.readString();
        this.terjemahan = in.readString();
    }

    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}

package com.anantadwi13.favouritemovie.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAV_FILM = "fav_film";

    public static class FavFilmColumns implements BaseColumns{
        public static String FILM_ID = "film_id",
                VOTE_COUNT = "vote_count",
                VOTE_AVERAGE = "vote_average",
                TITLE = "title",
                OVERVIEW = "overview",
                RELEASE_DATE = "release_date",
                POSTER_URL = "posterURL",
                ORIGINAL_LANG = "original_lang";
    }

    public static final String AUTHORITY = "com.anantadwi13.cataloguemovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).appendPath(TABLE_FAV_FILM).build();


}

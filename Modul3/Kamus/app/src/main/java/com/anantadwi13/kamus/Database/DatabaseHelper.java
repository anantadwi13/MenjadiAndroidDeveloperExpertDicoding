package com.anantadwi13.kamus.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.anantadwi13.kamus.Database.DatabaseContract.TABLE_NAME_ENG_IND;
import static com.anantadwi13.kamus.Database.DatabaseContract.KamusCol._ID;
import static com.anantadwi13.kamus.Database.DatabaseContract.KamusCol.KATA;
import static com.anantadwi13.kamus.Database.DatabaseContract.KamusCol.TERJEMAHAN;
import static com.anantadwi13.kamus.Database.DatabaseContract.TABLE_NAME_IND_ENG;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "kamus";
    private static int DB_VERSION = 1;
    private static String CREATE_TABLE_KAMUS_ENG_IND = "create table "+TABLE_NAME_ENG_IND +
            " ("+ _ID +" integer primary key autoincrement, " +
            KATA+" text not null, " +
            TERJEMAHAN+" text not null)";
    private static String CREATE_TABLE_KAMUS_IND_ENG = "create table "+TABLE_NAME_IND_ENG +
            " ("+ _ID +" integer primary key autoincrement, " +
            KATA+" text not null, " +
            TERJEMAHAN+" text not null)";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_KAMUS_IND_ENG);
        sqLiteDatabase.execSQL(CREATE_TABLE_KAMUS_ENG_IND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_ENG_IND);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_IND_ENG);
        onCreate(sqLiteDatabase);
    }
}

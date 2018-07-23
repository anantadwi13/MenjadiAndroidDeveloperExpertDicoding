package com.anantadwi13.kamus.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;

import com.anantadwi13.kamus.Model.Word;

import java.util.ArrayList;

import static com.anantadwi13.kamus.Database.DatabaseContract.KamusCol.KATA;
import static com.anantadwi13.kamus.Database.DatabaseContract.KamusCol.TERJEMAHAN;
import static com.anantadwi13.kamus.Database.DatabaseContract.TABLE_NAME_ENG_IND;
import static com.anantadwi13.kamus.Database.DatabaseContract.KamusCol._ID;
import static com.anantadwi13.kamus.Database.DatabaseContract.TABLE_NAME_IND_ENG;

public class KamusDB {
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    public static final int TYPE_ENG_IND = 1, TYPE_IND_ENG = 2;
    private String TABLE_NAME=TABLE_NAME_ENG_IND;

    public KamusDB(Context context, int type) {
        this.context = context;
        switch (type){
            case TYPE_ENG_IND:
                TABLE_NAME = TABLE_NAME_ENG_IND;
                break;
            case TYPE_IND_ENG:
                TABLE_NAME = TABLE_NAME_IND_ENG;
                break;
            default:
                TABLE_NAME = TABLE_NAME_ENG_IND;
        }
    }

    public KamusDB(Context context) {
        this.context = context;
    }

    public KamusDB open() throws SQLException{
        if (dbHelper==null || database==null) {
            dbHelper = new DatabaseHelper(context);
            database = dbHelper.getWritableDatabase();
        }
        return this;
    }

    public void close(){
        if (dbHelper!=null) {
            dbHelper.close();
            if (database!=null)
                database.close();
            dbHelper = null;
            database = null;
        }
    }

    private String getTableName(int TYPE){
        String TABLE_NAME;
        switch (TYPE){
            case TYPE_ENG_IND:
                TABLE_NAME = TABLE_NAME_ENG_IND;
                break;
            case TYPE_IND_ENG:
                TABLE_NAME = TABLE_NAME_IND_ENG;
                break;
            default:
                TABLE_NAME = this.TABLE_NAME;
        }
        return TABLE_NAME;
    }

    public Word getWord(int id,@Nullable Integer type){
        if (database == null) throw new SQLException("Open Database First!");

        String TABLE_NAME = this.TABLE_NAME;
        if (type!=null)
            TABLE_NAME = getTableName(type);

        Word kata = new Word();

        Cursor cursor = database.query(TABLE_NAME, null,_ID +"=?",new String[]{String.valueOf(id)},null,null,null);

        cursor.moveToFirst();
        kata.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
        kata.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
        kata.setTerjemahan(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAHAN)));

        cursor.close();
        return kata;
    }

    public ArrayList<Word> getWords(String cari, @Nullable Integer limit, @Nullable Integer type){
        if (database == null) throw new SQLException("Open Database First!");

        String TABLE_NAME = this.TABLE_NAME;
        if (type!=null)
            TABLE_NAME = getTableName(type);

        ArrayList<Word> words = new ArrayList<>();

        Cursor cursor;
        if (limit!=null)
            //cursor= database.query(TABLE_NAME, null,KATA +" LIKE ?",new String[]{"%"+cari+"%"},null,null,KATA+" ASC",String.valueOf(limit));
            cursor= database.query(TABLE_NAME, null,KATA +" LIKE ?",new String[]{cari+"%"},null,null,KATA+" ASC",String.valueOf(limit));
        else
            //cursor= database.query(TABLE_NAME, null,KATA +" LIKE ?",new String[]{"%"+cari+"%"},null,null,KATA+" ASC");
            cursor= database.query(TABLE_NAME, null,KATA +" LIKE ?",new String[]{cari+"%"},null,null,KATA+" ASC");

        while (cursor.moveToNext()){
            Word kata = new Word();
            kata.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
            kata.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
            kata.setTerjemahan(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAHAN)));
            words.add(kata);
        }

        cursor.close();
        return words;
    }

    public ArrayList<Word> getAllWords(@Nullable Integer limit,@Nullable Integer type){
        if (database == null) throw new SQLException("Open Database First!");

        String TABLE_NAME = this.TABLE_NAME;
        if (type!=null)
            TABLE_NAME = getTableName(type);

        ArrayList<Word> words = new ArrayList<>();

        Cursor cursor;
        if (limit!=null)
            cursor = database.query(TABLE_NAME, null,null,null,null,null,KATA+" ASC",String.valueOf(limit));
        else
            cursor = database.query(TABLE_NAME, null,null,null,null,null,KATA+" ASC");

        while (cursor.moveToNext()){
            Word kata = new Word();
            kata.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
            kata.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
            kata.setTerjemahan(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAHAN)));
            words.add(kata);
        }

        cursor.close();
        return words;
    }

    public long insert(Word kata,@Nullable Integer type){
        if (database == null) throw new SQLException("Open Database First!");

        String TABLE_NAME = this.TABLE_NAME;
        if (type!=null)
            TABLE_NAME = getTableName(type);

        ContentValues cv = new ContentValues();
        cv.put(KATA, kata.getKata());
        cv.put(TERJEMAHAN, kata.getTerjemahan());
        return database.insert(TABLE_NAME,null, cv);
    }

    public void insertTransaction(Word kata,@Nullable Integer type){
        if (database == null) throw new SQLException("Open Database First!");

        String TABLE_NAME = this.TABLE_NAME;
        if (type!=null)
            TABLE_NAME = getTableName(type);

        String sql = "INSERT INTO "+TABLE_NAME+" ("+KATA+", "+TERJEMAHAN +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);

        stmt.bindString(1,kata.getKata());
        stmt.bindString(2,kata.getTerjemahan());

        stmt.execute();
        stmt.clearBindings();
    }

    public boolean insert(ArrayList<Word> words, @Nullable Integer type, @Nullable ProgressUpdate progress){
        boolean isSuccess = false;
        beginTransaction();
        double min=0, max=100, progressDiff = (max-min)/words.size();
        double progressNow = min;

        try {
            for (Word kata : words) {
                insertTransaction(kata,type);
                progressNow += progressDiff;
                if (progress!=null) progress.onProgressUpdate(min,max,progressNow);
            }
            setTransactionSuccess();
            isSuccess = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        endTransaction();
        return isSuccess;
    }

    public void setTransactionSuccess(){
        if (database == null) throw new SQLException("Open Database First!");
        database.setTransactionSuccessful();
    }

    public void beginTransaction(){
        if (database == null) throw new SQLException("Open Database First!");
        database.beginTransaction();
    }

    public void endTransaction(){
        if (database == null) throw new SQLException("Open Database First!");
        database.endTransaction();
    }

    public interface ProgressUpdate{
        void onProgressUpdate(double min,double max,double now);
    }
}

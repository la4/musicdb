package com.coderivium.sidorov.vadim.musicdb.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.coderivium.sidorov.vadim.musicdb.data.MusicContract.SongEntry;

public class MusicDB {

    private static final String LOG_TAG = MusicDB.class.getSimpleName();

    private static final String DATABASE_NAME = "musicdb";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_SONGS_TABLE = "CREATE TABLE " + SongEntry.TABLE_NAME + " (" +
            SongEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SongEntry.COLUMN_NAME + " TEXT" +
            ");";

    private DBHelper dbHelper;

    private SQLiteDatabase musicDatabase;

    private static MusicDB mInstance;

    public static MusicDB getInstance() {
        if (mInstance == null) {
            mInstance = new MusicDB();
        }

        return mInstance;
    }

    private MusicDB() {

    }

    public void openConnection(Context context) {
        dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        musicDatabase = dbHelper.getWritableDatabase();
    }

    public void closeConnection() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public Cursor getAllSongs() {
        return musicDatabase.query(SongEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    public void addSong(String songName) {
        ContentValues cv = new ContentValues();
        cv.put(SongEntry.COLUMN_NAME, songName);
        musicDatabase.insert(SongEntry.TABLE_NAME, null, cv);
    }

    public void deleteSong(long id) {
        musicDatabase.delete(SongEntry.TABLE_NAME, SongEntry._ID + " = " + id, null);
    }

    private class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "onCreate of database");

            db.execSQL(SQL_CREATE_SONGS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

package com.coderivium.sidorov.vadim.musicdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DBHelper.class.getSimpleName();

    public DBHelper(Context context) {
        super(context, "musicDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "onCreate of database");

        final String createSongTableQuery =
                "CREATE TABLE songstable ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "songname TEXT,"
                        + ");";

        db.execSQL(createSongTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
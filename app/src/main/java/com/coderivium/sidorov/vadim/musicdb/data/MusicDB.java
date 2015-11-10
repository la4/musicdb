package com.coderivium.sidorov.vadim.musicdb.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MusicDB {

    private static final String LOG_TAG = MusicDB.class.getSimpleName();

    private static final String DB_NAME = "musicDB";

    private static final int DB_VERSION = 1;

    private static final String createSongTableQuery =
            "CREATE TABLE songstable ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "songname TEXT,"
                    + ");";

    private class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context) {
            super(context, "musicDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "onCreate of database");



            db.execSQL(createSongTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

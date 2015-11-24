package com.coderivium.sidorov.vadim.musicdb.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.coderivium.sidorov.vadim.musicdb.data.MusicContract.*;

public class MusicDB {

    private static final String LOG_TAG = MusicDB.class.getSimpleName();

    private static final String DATABASE_NAME = "musicdb";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_SONGS_TABLE = "CREATE TABLE " + SongEntry.TABLE_NAME + " (" +
            SongEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SongEntry.COLUMN_NAME + " TEXT, " +
            SongEntry.COLUMN_ALBUM + " TEXT, " +
            "FOREIGN KEY (" + SongEntry.COLUMN_ALBUM + ") REFERENCES " + AlbumEntry.TABLE_NAME + " (" + AlbumEntry._ID + ") ON DELETE CASCADE" +
            ");";

    private static final String SQL_CREATE_ALBUMS_TABLE = "CREATE TABLE " + AlbumEntry.TABLE_NAME + " (" +
            AlbumEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AlbumEntry.COLUMN_NAME + " TEXT UNIQUE, " +
            AlbumEntry.COLUMN_ARTIST + " TEXT, " +
            "FOREIGN KEY (" + AlbumEntry.COLUMN_ARTIST + ") REFERENCES " + ArtistEntry.TABLE_NAME + " (" + ArtistEntry._ID+ ") ON DELETE CASCADE" +
            ");";

    private static final String SQL_CREATE_ARTISTS_TABLE = "CREATE TABLE " + ArtistEntry.TABLE_NAME + " (" +
            ArtistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ArtistEntry.COLUMN_NAME + " TEXT UNIQUE" +
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

    public void addSong(String songName, String albumName, String artistName) {
        ContentValues songCV = new ContentValues();

        songCV.put(SongEntry.COLUMN_NAME, songName);

        String [] selectionArgs = new String[] { artistName };

        musicDatabase.beginTransaction();
        Cursor artistCursor = musicDatabase.query(ArtistEntry.TABLE_NAME, null, ArtistEntry.COLUMN_NAME + " = ?",  selectionArgs, null, null, null);
        artistCursor.moveToFirst();

        if (artistCursor.getCount() <= 0) {
            ContentValues artistCV = new ContentValues();

            // Adding artist
            artistCV.put(ArtistEntry.COLUMN_NAME, artistName);
            musicDatabase.insert(ArtistEntry.TABLE_NAME, null, artistCV);
            artistCursor = musicDatabase.query(ArtistEntry.TABLE_NAME, null, ArtistEntry.COLUMN_NAME + " = ?",  selectionArgs, null, null, null);
            artistCursor.moveToFirst();
        }

        selectionArgs = new String[] { albumName };
        Cursor albumCursor = musicDatabase.query(AlbumEntry.TABLE_NAME, null, AlbumEntry.COLUMN_NAME + " = ?", selectionArgs, null, null, null);
        albumCursor.moveToFirst();

        if (albumCursor.getCount() <= 0) {
            ContentValues albumCV = new ContentValues();


            albumCV.put(AlbumEntry.COLUMN_NAME, albumName);
            albumCV.put(AlbumEntry.COLUMN_ARTIST, artistCursor.getInt(artistCursor.getColumnIndex(ArtistEntry._ID)));

            musicDatabase.insert(AlbumEntry.TABLE_NAME, null, albumCV);
            albumCursor = musicDatabase.query(AlbumEntry.TABLE_NAME, null, AlbumEntry.COLUMN_NAME + " = ?", selectionArgs, null, null, null);
            albumCursor.moveToFirst();
        }

        songCV.put(SongEntry.COLUMN_ALBUM, albumCursor.getInt(albumCursor.getColumnIndex(AlbumEntry._ID)));

        musicDatabase.insert(SongEntry.TABLE_NAME, null, songCV);

        musicDatabase.setTransactionSuccessful();
        musicDatabase.endTransaction();
    }

    public void deleteSong(long id) {
        String []whereArgs = new String [] { String.valueOf(id) };
        musicDatabase.delete(SongEntry.TABLE_NAME, SongEntry._ID + " = ?", whereArgs);
    }

    public Cursor getAllAlbums() {
        return musicDatabase.query(AlbumEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    public void deleteAlbum(long id) {
        musicDatabase.delete(AlbumEntry.TABLE_NAME, AlbumEntry._ID + " = " + id, null);
    }

    public Cursor getAllArtists() {
        return musicDatabase.query(ArtistEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    public void deleteArtist(long id) {
        Log.d(LOG_TAG, musicDatabase.delete(ArtistEntry.TABLE_NAME, ArtistEntry._ID + " = " + id, null) + "");
    }

    private class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_SONGS_TABLE);
            db.execSQL(SQL_CREATE_ALBUMS_TABLE);
            db.execSQL(SQL_CREATE_ARTISTS_TABLE);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

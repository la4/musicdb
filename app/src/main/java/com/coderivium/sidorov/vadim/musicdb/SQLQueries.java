package com.coderivium.sidorov.vadim.musicdb;

import com.coderivium.sidorov.vadim.musicdb.data.MusicContract.*;

public class SQLQueries {

    public static final String SQL_CREATE_SONGS_TABLE = "CREATE TABLE " + SongEntry.TABLE_NAME + " (" +
            SongEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SongEntry.COLUMN_NAME + " TEXT, " +
            SongEntry.COLUMN_ALBUM + " TEXT, " +
            "FOREIGN KEY (" + SongEntry.COLUMN_ALBUM + ") REFERENCES " + AlbumEntry.TABLE_NAME + " (" + AlbumEntry._ID + ") ON DELETE CASCADE" +
            ");";

    public static final String SQL_CREATE_ALBUMS_TABLE = "CREATE TABLE " + AlbumEntry.TABLE_NAME + " (" +
                    AlbumEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AlbumEntry.COLUMN_NAME + " TEXT, " +
                    AlbumEntry.COLUMN_ARTIST + " TEXT, " +
                    "FOREIGN KEY (" + AlbumEntry.COLUMN_ARTIST + ") REFERENCES " + ArtistEntry.TABLE_NAME + " (" + ArtistEntry._ID+ ") ON DELETE CASCADE" +
                    ");";

    public static final String SQL_CREATE_ARTISTS_TABLE = "CREATE TABLE " + ArtistEntry.TABLE_NAME + " (" +
                            ArtistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            ArtistEntry.COLUMN_NAME + " TEXT UNIQUE" +
                            ");";

    // Seems that we need _id for CursorLoader to work properly
    public static final String SQL_GET_SONGS = "SELECT " +
            SongEntry.TABLE_NAME + "." + SongEntry._ID + " AS " + SongJoinEntry._ID + ", " +
            SongEntry.TABLE_NAME + "." + SongEntry.COLUMN_NAME + " AS " + SongJoinEntry.COLUMN_SONG_NAME + ", " +
            AlbumEntry.TABLE_NAME + "." + AlbumEntry.COLUMN_NAME + " AS " + SongJoinEntry.COLUMN_ALBUM_NAME + ", " +
            ArtistEntry.TABLE_NAME + "." + ArtistEntry.COLUMN_NAME + " AS " + SongJoinEntry.COLUMN_ARTIST_NAME + " " +

            "FROM " + SongEntry.TABLE_NAME + " INNER JOIN " + AlbumEntry.TABLE_NAME + " " +
            "ON " + SongEntry.TABLE_NAME + "." + SongEntry.COLUMN_ALBUM + " = " + AlbumEntry.TABLE_NAME + "." + AlbumEntry._ID + " " +
            "INNER JOIN " + ArtistEntry.TABLE_NAME + " ON " + AlbumEntry.TABLE_NAME + "." + AlbumEntry.COLUMN_ARTIST + " = " + ArtistEntry.TABLE_NAME + "." + ArtistEntry._ID + ";";

}
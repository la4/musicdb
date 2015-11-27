package com.coderivium.sidorov.vadim.musicdb.data;

import android.content.Context;
import android.database.Cursor;

public interface DatabaseMusic {

    void openConnection(Context context);

    void closeConnection();

    Cursor getAllSongs();

    void addSong(String songName, String albumName, String artistName);

    void deleteSong(long id);

    Cursor getAllAlbums();

    void deleteAlbum(long id);

    Cursor getAllArtists();

    void deleteArtist(long id);
}


package com.coderivium.sidorov.vadim.musicdb.data;

import android.content.Context;
import android.database.Cursor;

public interface DatabaseMusic {

    void openConnection(Context context);

    void closeConnection();

    void addSong(String songName, String albumName, String artistName);

    void deleteSong(long id);

    void deleteAlbum(long id);

    void deleteArtist(long id);

}


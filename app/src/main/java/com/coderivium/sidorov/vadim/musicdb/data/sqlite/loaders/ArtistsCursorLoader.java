package com.coderivium.sidorov.vadim.musicdb.data.sqlite.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.coderivium.sidorov.vadim.musicdb.data.DatabaseMusic;
import com.coderivium.sidorov.vadim.musicdb.data.SQLiteMusic;

public class ArtistsCursorLoader extends CursorLoader {

    DatabaseMusic database;

    public ArtistsCursorLoader(Context context, DatabaseMusic database) {
        super(context);
        this.database = database;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = ((SQLiteMusic)database).getAllArtists();
        return cursor;
    }
}

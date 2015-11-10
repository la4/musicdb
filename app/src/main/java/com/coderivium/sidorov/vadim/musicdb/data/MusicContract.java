package com.coderivium.sidorov.vadim.musicdb.data;

import android.provider.BaseColumns;

/*
    Defines our database scheme.
 */
public class MusicContract {

    public static final class SongEntry implements BaseColumns {

        public static final String TABLE_NAME = "songstable";

        public static final String COLUMN_NAME = "name";

    }
}

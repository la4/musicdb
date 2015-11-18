package com.coderivium.sidorov.vadim.musicdb.data;

import android.provider.BaseColumns;
import android.renderscript.BaseObj;

/*
    Defines our database scheme.
 */
public class MusicContract {

    public static class SongEntry implements BaseColumns {

        public static final String TABLE_NAME = "songstable";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_ALBUM = "album";
    }

    public static class AlbumEntry implements BaseColumns {

        public static final String TABLE_NAME = "albumstable";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_ARTIST = "artist";
    }

    public static class ArtistEntry implements BaseColumns {

        public static final String TABLE_NAME = "artiststable";

        public static final String COLUMN_NAME = "name";
    }
}

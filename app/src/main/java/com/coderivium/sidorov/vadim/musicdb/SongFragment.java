package com.coderivium.sidorov.vadim.musicdb;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.coderivium.sidorov.vadim.musicdb.data.MusicContract;
import com.coderivium.sidorov.vadim.musicdb.data.MusicDB;

public class SongFragment extends BaseFragment {

    private static final String LOG_TAG = SongFragment.class.getSimpleName();

    private ListView songsList;

    public static SongFragment newInstance() {
        SongFragment fragment = new SongFragment();
        return fragment;
    }

    public SongFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_songs, container, false);

        // Working with database
        musicDB = MusicDB.getInstance();

        String[] from = new String[]{
                MusicContract.SongJoinEntry.COLUMN_SONG_NAME,
                MusicContract.SongJoinEntry.COLUMN_ALBUM_NAME,
                MusicContract.SongJoinEntry.COLUMN_ARTIST_NAME,
        };

        int[] to = new int[]{
                R.id.songName,
                R.id.albumName,
                R.id.artistName
        };

        // Setting adapter
        cursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.element_list_song, null, from, to, 0);
        songsList = (ListView) rootView.findViewById(R.id.songsListView);
        songsList.setAdapter(cursorAdapter);


        getActivity().getSupportLoaderManager().initLoader(Constants.SONGS_LOADER_ID, null, this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerForContextMenu(songsList);
    }

    @Override
    public void onPause() {
        unregisterForContextMenu(songsList);
        super.onPause();
    }

    @Override
    protected void deleteRecord(long id) {
        musicDB.deleteSong(id);

        // Updating loaders/listviews
        updateLoader(Constants.SONGS_LOADER_ID);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new SongsLoader(getContext(), musicDB);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class SongsLoader extends CursorLoader {

        MusicDB database;

        public SongsLoader(Context context, MusicDB database) {
            super(context);
            this.database = database;
        }

        @Override
        public Cursor loadInBackground() {

            Cursor cursor = database.getAllSongs();
            return cursor;
        }
    }
}

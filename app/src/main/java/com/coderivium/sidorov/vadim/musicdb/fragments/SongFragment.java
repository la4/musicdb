package com.coderivium.sidorov.vadim.musicdb.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.coderivium.sidorov.vadim.musicdb.Constants;
import com.coderivium.sidorov.vadim.musicdb.R;
import com.coderivium.sidorov.vadim.musicdb.data.sqlite.SQLContract;
import com.coderivium.sidorov.vadim.musicdb.data.RealmDatabase;
import com.coderivium.sidorov.vadim.musicdb.data.SQLiteMusic;
import com.coderivium.sidorov.vadim.musicdb.data.realm.RealmSong;
import com.coderivium.sidorov.vadim.musicdb.data.realm.adapters.RealmSongAdapter;
import com.coderivium.sidorov.vadim.musicdb.data.sqlite.loaders.SongsCursorLoader;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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
        Log.d(LOG_TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_songs, container, false);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        defaultDatabase = getResources().getString(R.string.pref_default_value);
        currentDatabase = sharedPref.getString(getString(R.string.pref_dbtype_key), defaultDatabase);

        // Working with database
        if (currentDatabase.equals(getString(R.string.pref_sqlite_value))) {
            database = SQLiteMusic.getInstance();
        } else {
            database = RealmDatabase.getInstance();
        }

        if (currentDatabase.equals(getString(R.string.pref_sqlite_value))) {
            //Adapter for SQLite database
            String[] from = new String[]{
                    SQLContract.SongJoinEntry.COLUMN_SONG_NAME,
                    SQLContract.SongJoinEntry.COLUMN_ALBUM_NAME,
                    SQLContract.SongJoinEntry.COLUMN_ARTIST_NAME,
            };

            int[] to = new int[]{
                    R.id.songName,
                    R.id.albumName,
                    R.id.artistName
            };

            cursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.element_list_song, null, from, to, 0);
        } else {
            Realm realm = Realm.getInstance(getContext());
            RealmQuery<RealmSong> query = realm.where(RealmSong.class);
            RealmResults<RealmSong> results = query.findAll();

            // Adapter for Realm database
            cursorAdapter = new RealmSongAdapter(getContext(), R.layout.element_list_song, results, true);
        }

        songsList = (ListView) rootView.findViewById(R.id.songsListView);
        songsList.setAdapter(cursorAdapter);

        if (currentDatabase.equals(getString(R.string.pref_sqlite_value))) {
            getActivity().getSupportLoaderManager().initLoader(Constants.SONGS_LOADER_ID, null, this);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
        registerForContextMenu(songsList);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
        unregisterForContextMenu(songsList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    protected void deleteRecord(long id) {

        database.deleteSong(id);

        // Updating loaders/listviews
        updateLoader(Constants.SONGS_LOADER_ID);
    }

    // For SQLite loaders
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Log.d(LOG_TAG, "onCreateLoader");
        return new SongsCursorLoader(getContext(), database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(LOG_TAG, "onLoadFinished");
        ((SimpleCursorAdapter)cursorAdapter).swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "onLoaderReset");
    }
}

package com.coderivium.sidorov.vadim.musicdb.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
import com.coderivium.sidorov.vadim.musicdb.data.realm.RealmAlbum;
import com.coderivium.sidorov.vadim.musicdb.data.realm.adapters.RealmAlbumAdapter;
import com.coderivium.sidorov.vadim.musicdb.data.sqlite.loaders.AlbumsCursorLoader;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the“‘“
 * to handle interaction events.
 * Use the {@link AlbumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumsFragment extends BaseFragment {

    private static final String LOG_TAG = AlbumsFragment.class.getSimpleName();

    private ListView albumsList;

    public static AlbumsFragment newInstance() {
        AlbumsFragment fragment = new AlbumsFragment();
        return fragment;
    }

    public AlbumsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_albums, container, false);

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
                    SQLContract.AlbumEntry.COLUMN_NAME
            };

            int[] to = new int[]{
                    R.id.albumName
            };

            cursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.element_list_album, null, from, to, 0);
        } else {
            Realm realm = Realm.getInstance(getContext());
            RealmQuery<RealmAlbum> query = realm.where(RealmAlbum.class);
            RealmResults<RealmAlbum> results = query.findAll();

            // Adapter for Realm database
            cursorAdapter = new RealmAlbumAdapter(getContext(), R.layout.element_list_album, results, true);
        }

        albumsList = (ListView) rootView.findViewById(R.id.albumsListView);
        albumsList.setAdapter(cursorAdapter);

        if (currentDatabase.equals(getString(R.string.pref_sqlite_value))) {
            getActivity().getSupportLoaderManager().initLoader(Constants.ALBUMS_LOADER_ID, null, this);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
        registerForContextMenu(albumsList);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
        unregisterForContextMenu(albumsList);
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
        database.deleteAlbum(id);

        // Updating loaders/listviews
        updateLoader(Constants.SONGS_LOADER_ID);
        updateLoader(Constants.ALBUMS_LOADER_ID);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Log.d(LOG_TAG, "onCreateLoader");
        return new AlbumsCursorLoader(getContext(), database);
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

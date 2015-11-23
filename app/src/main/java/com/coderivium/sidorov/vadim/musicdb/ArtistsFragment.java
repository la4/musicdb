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


public class ArtistsFragment extends BaseFragment {

    private static final String LOG_TAG = ArtistsFragment.class.getSimpleName();

    private ListView artistsList;

    public static ArtistsFragment newInstance() {
        ArtistsFragment fragment = new ArtistsFragment();
        return fragment;
    }

    public ArtistsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artists, container, false);

        // Working with database
        musicDB = MusicDB.getInstance();

        String[] from = new String[]{
                MusicContract.ArtistEntry.COLUMN_NAME
        };

        int[] to = new int[]{
                R.id.artistName
        };

        // Setting adapter
        cursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.element_list_artist, null, from, to, 0);
        artistsList = (ListView) rootView.findViewById(R.id.artistsListView);
        artistsList.setAdapter(cursorAdapter);


        getActivity().getSupportLoaderManager().initLoader(Constants.ARTISTS_LOADER_ID, null, this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerForContextMenu(artistsList);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterForContextMenu(artistsList);
    }

    @Override
    protected void deleteRecord(long id) {

        musicDB.deleteArtist(id);

        // Updating loaders/listviews
        updateLoader(Constants.SONGS_LOADER_ID);
        updateLoader(Constants.ALBUMS_LOADER_ID);
        updateLoader(Constants.ARTISTS_LOADER_ID);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new ArtistsCursor(getContext(), musicDB);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class ArtistsCursor extends CursorLoader {

        MusicDB database;

        public ArtistsCursor(Context context, MusicDB database) {
            super(context);
            this.database = database;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = database.getAllArtists();
            return cursor;
        }
    }
}

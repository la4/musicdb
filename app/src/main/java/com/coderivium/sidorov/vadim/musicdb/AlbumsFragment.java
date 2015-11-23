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
        View rootView = inflater.inflate(R.layout.fragment_albums, container, false);

        // Working with database
        musicDB = MusicDB.getInstance();

        String[] from = new String[]{
                MusicContract.AlbumEntry.COLUMN_NAME
        };

        int[] to = new int[]{
                R.id.albumName
        };

        // Setting adapter
        cursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.element_list_album, null, from, to, 0);
        albumsList = (ListView) rootView.findViewById(R.id.albumsListView);
        albumsList.setAdapter(cursorAdapter);

        getActivity().getSupportLoaderManager().initLoader(Constants.ALBUMS_LOADER_ID, null, this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerForContextMenu(albumsList);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterForContextMenu(albumsList);
    }

    @Override
    protected void deleteRecord(long id) {
        musicDB.deleteAlbum(id);

        // Updating loaders/listviews
        updateLoader(Constants.SONGS_LOADER_ID);
        updateLoader(Constants.ALBUMS_LOADER_ID);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new AlbumsCursorLoader(getContext(), musicDB);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class AlbumsCursorLoader extends CursorLoader {

        MusicDB database;

        public AlbumsCursorLoader(Context context, MusicDB database) {
            super(context);
            this.database = database;
        }

        @Override
        public Cursor loadInBackground() {

            Cursor cursor = database.getAllAlbums();
            return cursor;
        }
    }
}

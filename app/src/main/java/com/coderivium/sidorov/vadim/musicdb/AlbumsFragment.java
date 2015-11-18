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
public class AlbumsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String LOG_TAG = AlbumsFragment.class.getSimpleName();

    private static final int CM_DELETE_ID = 1;

    private ListView albumsList;

    private SimpleCursorAdapter cursorAdapter;

    private MusicDB musicDB;


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

        int[] to = new int[] {
                R.id.albumName
        };

        // Setting adapter
        cursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.element_list_album, null, from, to, 0);
        albumsList = (ListView)rootView.findViewById(R.id.albumsListView);
        albumsList.setAdapter(cursorAdapter);

        registerForContextMenu(albumsList);

        getActivity().getSupportLoaderManager().initLoader(1, null, this);
        return rootView;
    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            musicDB.deleteAlbum(acmi.id);
            // получаем новый курсор с данными
            getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new AlbumsCursor(getContext(), musicDB);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class AlbumsCursor extends CursorLoader {

        MusicDB database;

        public AlbumsCursor(Context context, MusicDB database) {
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

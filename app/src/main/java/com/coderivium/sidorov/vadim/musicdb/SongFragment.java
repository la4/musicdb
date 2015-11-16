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

import java.util.concurrent.TimeUnit;

public class SongFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int CM_DELETE_ID = 1;

    private ListView songsList;

    private SimpleCursorAdapter cursorAdapter;

    private MusicDB musicDB;


    public static SongFragment newInstance(int sectionNumber) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SongFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song, container, false);


        // Working with database
        musicDB = MusicDB.getInstance();

        String[] from = new String[]{
                MusicContract.SongEntry.COLUMN_NAME
        };

        int[] to = new int[] {
                R.id.songName
        };

        cursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.element_list, null, from, to, 0);
        songsList = (ListView)rootView.findViewById(R.id.songsListView);
        songsList.setAdapter(cursorAdapter);

        // добавляем контекстное меню к списку
        registerForContextMenu(songsList);

        // создаем лоадер для чтения данных
        getActivity().getSupportLoaderManager().initLoader(0, null, this);

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
            musicDB.deleteSong(acmi.id);
            // получаем новый курсор с данными
            getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new MyCursorLoader(getContext(), musicDB);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        MusicDB database;

        public MyCursorLoader(Context context, MusicDB database) {
            super(context);
            this.database = database;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = database.getAllSongs();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }
    }
}

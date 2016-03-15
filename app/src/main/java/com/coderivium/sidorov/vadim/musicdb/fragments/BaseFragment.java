package com.coderivium.sidorov.vadim.musicdb.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import com.coderivium.sidorov.vadim.musicdb.Constants;
import com.coderivium.sidorov.vadim.musicdb.R;
import com.coderivium.sidorov.vadim.musicdb.data.DatabaseMusic;
import com.coderivium.sidorov.vadim.musicdb.data.SQLiteMusic;

public abstract class BaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    protected ListAdapter cursorAdapter;

    protected DatabaseMusic database;

    protected SharedPreferences sharedPref;
    protected String defaultDatabase;
    protected String currentDatabase;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, Constants.CM_DELETE_ID, 0, R.string.delete_album);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (getUserVisibleHint() && item.getItemId() == Constants.CM_DELETE_ID) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            deleteRecord(acmi.id);

            return true;
        }
        return super.onContextItemSelected(item);
    }

    protected abstract void deleteRecord(long id);

    protected void updateLoader(int loaderId) {
        Loader<Object> loader = getActivity().getSupportLoaderManager().getLoader(loaderId);

        if (loader != null) {
            loader.forceLoad();
        }
    }
}

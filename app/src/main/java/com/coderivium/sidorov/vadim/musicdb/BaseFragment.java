package com.coderivium.sidorov.vadim.musicdb;


import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;

import com.coderivium.sidorov.vadim.musicdb.data.MusicDB;

public abstract class BaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    protected SimpleCursorAdapter cursorAdapter;

    protected MusicDB musicDB;

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

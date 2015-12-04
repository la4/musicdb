package com.coderivium.sidorov.vadim.musicdb.data.realm.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.coderivium.sidorov.vadim.musicdb.R;
import com.coderivium.sidorov.vadim.musicdb.data.realm.RealmAlbum;
import com.coderivium.sidorov.vadim.musicdb.data.realm.RealmSong;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;


public class RealmAlbumAdapter extends RealmBaseAdapter<RealmAlbum> implements ListAdapter {

    private int resId;

    public RealmAlbumAdapter(Context context, int resId, RealmResults<RealmAlbum> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.resId = resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(resId, parent, false);
        }

        RealmAlbum album = realmResults.get(position);

        ((TextView) convertView.findViewById(R.id.albumName)).setText(album.getName());

        return convertView;
    }

    public RealmResults<RealmAlbum> getRealmResults() {
        return realmResults;
    }
}

package com.coderivium.sidorov.vadim.musicdb.data.realm.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.coderivium.sidorov.vadim.musicdb.R;
import com.coderivium.sidorov.vadim.musicdb.data.realm.RealmAlbum;
import com.coderivium.sidorov.vadim.musicdb.data.realm.RealmArtist;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class RealmArtistAdapter extends RealmBaseAdapter<RealmArtist> implements ListAdapter {

    private int resId;

    public RealmArtistAdapter(Context context, int resId, RealmResults<RealmArtist> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.resId = resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(resId, parent, false);
        }

        RealmArtist artist = realmResults.get(position);

        ((TextView) convertView.findViewById(R.id.artistName)).setText(artist.getName());

        return convertView;
    }

    public RealmResults<RealmArtist> getRealmResults() {
        return realmResults;
    }
}

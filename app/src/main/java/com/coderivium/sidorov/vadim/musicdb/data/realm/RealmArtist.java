package com.coderivium.sidorov.vadim.musicdb.data.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmArtist extends RealmObject{

    @PrimaryKey
    private String name;

    private RealmList<RealmAlbum> albums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<RealmAlbum> getAlbums() {
        return albums;
    }

    public void setAlbums(RealmList<RealmAlbum> albums) {
        this.albums = albums;
    }

}

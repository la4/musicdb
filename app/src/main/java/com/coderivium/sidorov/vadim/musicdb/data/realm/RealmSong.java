package com.coderivium.sidorov.vadim.musicdb.data.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmSong extends RealmObject {

    @PrimaryKey
    private int id;

    private String name;

    private RealmAlbum album;

    // Standard getters and setters that will later be overridden by proxy classes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmAlbum getAlbum() {
        return album;
    }

    public void setAlbum(RealmAlbum album) {
        this.album = album;
    }

}

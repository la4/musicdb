package com.coderivium.sidorov.vadim.musicdb.data.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmAlbum extends RealmObject{

    @PrimaryKey
    private int id;

    private String name;

    private RealmArtist artist;

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

    public RealmArtist getArtist() {
        return artist;
    }

    public void setArtist(RealmArtist artist) {
        this.artist = artist;
    }
}

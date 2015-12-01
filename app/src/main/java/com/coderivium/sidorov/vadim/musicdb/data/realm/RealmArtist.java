package com.coderivium.sidorov.vadim.musicdb.data.realm;

import io.realm.annotations.PrimaryKey;

public class RealmArtist {

    @PrimaryKey
    private int id;

    private String name;

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

}

package com.coderivium.sidorov.vadim.musicdb.data.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmArtist extends RealmObject{

    @PrimaryKey
    private String name;

    // Standard getters and setters that will later be overridden by proxy classes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

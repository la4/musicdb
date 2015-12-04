package com.coderivium.sidorov.vadim.musicdb.data;

import android.content.Context;
import android.util.Log;

import com.coderivium.sidorov.vadim.musicdb.data.realm.RealmAlbum;
import com.coderivium.sidorov.vadim.musicdb.data.realm.RealmArtist;
import com.coderivium.sidorov.vadim.musicdb.data.realm.RealmSong;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmDatabase implements DatabaseMusic {

    private static final String LOG_TAG = RealmDatabase.class.getSimpleName();

    private Realm realm;

    public static DatabaseMusic getInstance() {
        return SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        public static final DatabaseMusic mInstance = new RealmDatabase();
    }

    private RealmDatabase() {
    }

    public void openConnection(Context context) {
        realm = Realm.getInstance(context);
    }

    public void closeConnection() {
        if (realm != null) {
            realm.close();
        }
    }

    public void addSong(String songName, String albumName, String artistName) {
        Log.d(LOG_TAG, "add song");


        RealmSong song = new RealmSong();
        song.setName(songName);

        RealmQuery<RealmArtist> artistQuery = realm
                .where(RealmArtist.class)
                .equalTo("name", artistName);

        RealmResults<RealmArtist> artist = artistQuery.findAll();

        if (artist.size() == 0) {
            realm.beginTransaction();

            RealmArtist realmArtist = new RealmArtist();
            realmArtist.setName(artistName);
            realm.copyToRealm(realmArtist);

            realm.commitTransaction();

            artist = artistQuery.findAll(); //do i need this ?? debug
        }

        RealmQuery<RealmAlbum> albumQuery = realm
                .where(RealmAlbum.class)
                .equalTo("name", albumName)
                .equalTo(, artist.iterator().next())


        realm.copyToRealm(song);

        realm.commitTransaction();
    }

    public void deleteSong(long id) {
        Log.d(LOG_TAG, "deleteSong");
    }

    public void deleteAlbum(long id) {
        Log.d(LOG_TAG, "deleteAlbum");

    }

    public void deleteArtist(long id) {
        Log.d(LOG_TAG, "deleteArtist");
    }

    public int getNextSongId() {
        return (int)realm.where(RealmSong.class).maximumInt("id_cp") + 1;
    }
}

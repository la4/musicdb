package com.coderivium.sidorov.vadim.musicdb;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.coderivium.sidorov.vadim.musicdb.R;

public class DBPreferences extends PreferenceFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}

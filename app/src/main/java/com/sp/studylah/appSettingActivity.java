package com.sp.studylah;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class appSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_layout, new preferencesFragment())
                .commit();
    }
}
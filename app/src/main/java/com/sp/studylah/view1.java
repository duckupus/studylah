package com.sp.studylah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class view1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view1);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

}
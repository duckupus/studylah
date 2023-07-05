package com.sp.studylah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class view2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

}
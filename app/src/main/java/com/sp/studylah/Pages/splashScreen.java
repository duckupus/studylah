package com.sp.studylah.Pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sp.studylah.PageActivities.MainActivity;
import com.sp.studylah.R;

public class splashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //create splash screen for 3 seconds before going to main activity
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally { //always run this
                //start main activity
                Intent intent = new Intent(splashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        thread.start();
    }
}

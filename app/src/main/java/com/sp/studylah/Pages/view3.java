package com.sp.studylah.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sp.studylah.MainActivity;
import com.sp.studylah.R;
import com.sp.studylah.TimerService;
import com.sp.studylah.carousel_fragments.timer.TimeHelper;
import com.sp.studylah.carousel_fragments.timer.TimerHandler;

public class view3 extends AppCompatActivity {
    private TimerHandler handler;
    private TextView textView;
    private long timeRemaining;
    private TimeHelper timeHelper;
    private EditText editTextHour;
    private EditText editTextMinute;
    private EditText editTextSecond;
    private Button button;
    private Button toggleState;
    private ProgressBar progressBar;
    private final String COUNTDOWN_BR = "com.sp.studylah.countdown_timer_update";
    private final String COUNTDOWN_BR_SERVICE = "com.sp.studylah.countdown_timer_update2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view3);
        textView = findViewById(R.id.textTimerView);
        editTextHour = findViewById(R.id.editTextTimerViewHour);
        editTextMinute = findViewById(R.id.editTextTimerViewMinute);
        editTextSecond = findViewById(R.id.editTextTimerViewSecond);
        button = findViewById(R.id.buttonTimerView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputHour = editTextHour.getText().toString();
                String inputMinute = editTextMinute.getText().toString();
                String inputSecond = editTextSecond.getText().toString();
                int check = 0;
                int hour = 0;
                if(!inputHour.equals("")) {
                    hour = Integer.parseInt(inputHour);
                    check++;
                }
                int minute = 0;
                if(!inputMinute.equals("")) {
                    minute = Integer.parseInt(inputMinute);
                    check++;
                }
                int second = 0;
                if(!inputSecond.equals("")) {
                    second = Integer.parseInt(inputSecond);
                    check++;
                }
                if(check == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid value!", Toast.LENGTH_SHORT).show();
                } else {
                    Context context = getApplicationContext();

                    timeRemaining = (long) hour * 60 * 60 + minute * 60L + second;
                    timeRemaining *= 1000;
                    timeHelper = new TimeHelper(timeRemaining);
                    Intent intent = new Intent(context, TimerService.class);
                    intent.putExtra("timeHelper",timeHelper);
                    context.startForegroundService(intent);
                }
            }
        });
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(COUNTDOWN_BR)) {
                    timeHelper = (TimeHelper) intent.getSerializableExtra("timeHelper");
                    textView.setText(timeHelper.getTimeRemainingString());
                    progressBar.setProgress((int) (timeHelper.getProgress()/10), true);
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(COUNTDOWN_BR));
        toggleState = findViewById(R.id.buttonTimerViewStartStop);
        toggleState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeHelper != null) {
                    timeHelper.toggleState();
                    Intent intent = new Intent(COUNTDOWN_BR_SERVICE);
                    intent.putExtra("timeHelper",timeHelper);
                    sendBroadcast(intent);
                } else {
                    button.performClick();
                }
            }
        });
        progressBar = findViewById(R.id.progressBarTimerView);
        progressBar.setMax(100);
        progressBar.setProgress(0, true);
        if(timeHelper != null) {
            progressBar.setProgress((int) (timeHelper.getTimeRemaining()/10), true);
        }


        textView.setText("Time remaining: 00:00:00");

    }
    @Override
    public void finish() {
        super.finish();
        if(timeHelper != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("TimerInstance", timeHelper);
            startActivity(intent);
        }
    }
}
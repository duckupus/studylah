package com.sp.studylah.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sp.studylah.MainActivity;
import com.sp.studylah.R;
import com.sp.studylah.carousel_fragments.timer.TimeHelper;
import com.sp.studylah.carousel_fragments.timer.TimerHandler;

public class view3 extends AppCompatActivity {
    private TimerHandler handler;
    private TextView textView;
    private int timeRemaining;
    private TimeHelper timeHelper;
    private EditText editTextHour;
    private EditText editTextMinute;
    private EditText editTextSecond;
    private Button button;
    private Button toggleState;

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
                    timeRemaining = hour * 60 * 60 + minute * 60 + second;
                    timeHelper = new TimeHelper(timeRemaining, TimeHelper.TimeHelperHelper.DOWN);
                    handler = new TimerHandler(Looper.getMainLooper());
                    //handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (textView != null) {
                                textView.setText("Time remaining: "+ timeHelper.getTimeRemainingString());
                            }
                            if(timeHelper.isRunning()) {
                                timeHelper.tick();
                                if (timeHelper.getTimeRemaining() >= 0) {
                                    handler.postDelayed(this, 1000);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        textView.setText("Time remaining: 00:00:00");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            timeHelper = (TimeHelper) bundle.getSerializable("TimerInstance");
            handler = (TimerHandler) bundle.getSerializable("TimerHelper");
            if(handler != null && timeHelper != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textView != null) {
                            textView.setText("Time remaining: "+ timeHelper.getTimeRemainingString());
                        }
                        if(timeHelper.isRunning()) {
                            timeHelper.tick();
                            if (timeHelper.getTimeRemaining() >= 0) {
                                handler.postDelayed(this, 1000);
                            } else {
                                Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        }
        toggleState = findViewById(R.id.buttonTimerViewStartStop);
        toggleState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeHelper != null) {
                    if (timeHelper.isRunning()) {
                        timeHelper.stop();
                    } else {
                        timeHelper.cont();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (textView != null) {
                                    textView.setText("Time remaining: "+ timeHelper.getTimeRemainingString());
                                }
                                if(timeHelper.isRunning()) {
                                    timeHelper.tick();
                                    if (timeHelper.getTimeRemaining() >= 0) {
                                        handler.postDelayed(this, 1000);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No timer active!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("TimerInstance", timeHelper);
        intent.putExtra("TimerHelper", handler);
        startActivity(intent);
    }
}
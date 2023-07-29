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
    private TimerHandler countDownTimer;

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

                    if(timeHelper != null && countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    timeHelper = new TimeHelper(timeRemaining);
                    countDownTimer = new TimerHandler(timeRemaining * 1000L, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timeHelper.setTime((int) (millisUntilFinished / 1000));
                            textView.setText(timeHelper.getTimeRemainingString());

                        }

                        @Override
                        public void onFinish() {
                            textView.setText("Time's up!");
                        }
                    };
                    countDownTimer.start();
                }
            }
        });
        toggleState = findViewById(R.id.buttonTimerViewStartStop);
        toggleState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countDownTimer != null) {
                    if(timeHelper.isRunning()) {
                        countDownTimer.cancel();
                    } else {
                        countDownTimer = new TimerHandler(timeHelper.getTimeRemaining() * 1000L, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timeHelper.setTime((int) (millisUntilFinished / 1000));
                                textView.setText(timeHelper.getTimeRemainingString());

                            }

                            @Override
                            public void onFinish() {
                                textView.setText("Time's up!");
                            }
                        };
                        countDownTimer.start();
                    }
                    timeHelper.toggleState();
                } else {
                    button.performClick();
                }
            }
        });


        textView.setText("Time remaining: 00:00:00");

        Bundle bundle = getIntent().getExtras();
    }
    @Override
    public void finish() {
        super.finish();
        if(timeHelper != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("TimerInstance", timeHelper);
            startActivity(intent);
        }
        //intent.putExtra("TimerHelper", countDownTimer);
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
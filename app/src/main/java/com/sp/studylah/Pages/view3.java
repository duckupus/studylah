package com.sp.studylah.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sp.studylah.R;
import com.sp.studylah.carousel_fragments.timer.TimerHelper;

public class view3 extends AppCompatActivity {
    private Handler handler;
    private TextView textView;
    private int timeRemaining;
    private TimerHelper timerHelper;
    private EditText editTextHour;
    private EditText editTextMinute;
    private EditText editTextSecond;
    private Button button;

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
                if(!inputHour.equals("") && !inputMinute.equals("") && !inputSecond.equals("")) {
                    int hour = Integer.parseInt(inputHour);
                    int minute = Integer.parseInt(inputMinute);
                    int second = Integer.parseInt(inputSecond);
                    timeRemaining = hour * 60 * 60 + minute * 60 + second;
                    timerHelper = new TimerHelper(timeRemaining);
                    handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (textView != null) {
                                textView.setText("Time remaining: "+timerHelper.getTimeRemainingString());
                            }
                            timerHelper.decrementTimeRemaining();
                            if (timerHelper.getTimeRemaining() >= 0) {
                                handler.postDelayed(this, 1000);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView.setText("Time remaining: 00:00:00");

        timeRemaining = 2 *60*60 + 10*60 + 12;
        timerHelper = new TimerHelper(timeRemaining);
        handler = new Handler(Looper.getMainLooper());
        /*
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (textView != null) {
                    textView.setText(timerHelper.getTimeRemainingString());
                }
                timerHelper.decrementTimeRemaining();
                if (timerHelper.getTimeRemaining() >= 0) {
                    handler.postDelayed(this, 100);
                }
            }
        }); */

    }
}
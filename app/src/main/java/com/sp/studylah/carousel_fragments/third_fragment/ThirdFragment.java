package com.sp.studylah.carousel_fragments.third_fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sp.studylah.Pages.view3;
import com.sp.studylah.R;
import com.sp.studylah.carousel_fragments.timer.TimeHelper;
import com.sp.studylah.carousel_fragments.timer.TimerHandler;

public class ThirdFragment extends Fragment {

    private LinearLayout layout;
    private TimeHelper timeHelper;
    private TextView textView;
    private TimerHandler handler;
    private Button toggleState;
    private Button plus;
    private Button minus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = view.findViewById(R.id.fragment_third);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), view3.class);
                if(timeHelper != null) {
                    intent.putExtra("TimerInstance", timeHelper);
                }
                if(handler != null) {
                    handler.cancel();
                }
                startActivity(intent);
            }
        });
        textView = view.findViewById(R.id.displayTimer);
        toggleState = view.findViewById(R.id.start_stop_timer);
        toggleState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeHelper == null) {
                    textView.setText("No timer active!");
                } else {
                    if (timeHelper.isRunning()) {
                        if (handler != null) {
                            handler.cancel();
                            handler = null;
                        }
                    } else {
                        handler = new TimerHandler(timeHelper.getTimeRemaining(), 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timeHelper.setTime(millisUntilFinished);
                                textView.setText(timeHelper.getTimeRemainingString());
                            }

                            @Override
                            public void onFinish() {
                                textView.setText("Timer finished!");
                                timeHelper.toggleState();
                            }
                        };
                        handler.start();
                    }
                    timeHelper.toggleState();
                }
            }
        });
        plus = view.findViewById(R.id.addTimeButtton);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeHelper != null) {
                    timeHelper.setTime(timeHelper.getTimeRemaining() + 60*1000);
                    if(handler != null) {
                        handler.cancel();
                        handler = new TimerHandler(timeHelper.getTimeRemaining(), 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timeHelper.setTime(millisUntilFinished);
                                textView.setText(timeHelper.getTimeRemainingString());
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                textView.setText("Timer has ended");
                                handler = null;
                                timeHelper = null;
                            }
                        };
                    }
                    textView.setText(timeHelper.getTimeRemainingString());
                } else textView.setText("No timer active!");
            }
        });
        minus = view.findViewById(R.id.subTimeButton);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeHelper != null) {
                    timeHelper.setTime(timeHelper.getTimeRemaining() - 60*1000);
                    if(handler != null) {
                        handler.cancel();
                        handler = new TimerHandler(timeHelper.getTimeRemaining(), 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timeHelper.setTime(millisUntilFinished);
                                textView.setText(timeHelper.getTimeRemainingString());
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                textView.setText("Timer has ended");
                                handler = null;
                                timeHelper = null;
                            }
                        };
                    }
                    textView.setText(timeHelper.getTimeRemainingString());
                } else textView.setText("No timer active!");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null) {
            timeHelper = (TimeHelper) bundle.getSerializable("TimerInstance");
            if(timeHelper != null) {
                textView.setText(timeHelper.getTimeRemainingString());
                if(timeHelper.isRunning()) {
                    handler = new TimerHandler(timeHelper.getTimeRemaining(), 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timeHelper.setTime(millisUntilFinished);
                            textView.setText(timeHelper.getTimeRemainingString());
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            textView.setText("Timer has ended");
                            handler = null;
                            timeHelper = null;
                        }
                    };
                    handler.start();
                }
            } else {
                textView.setText("No timer set");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
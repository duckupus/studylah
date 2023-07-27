package com.sp.studylah.carousel_fragments.timer;

import android.os.Handler;
import android.os.Looper;

import java.io.Serializable;

public class TimerHandler extends Handler implements Serializable {
    public TimerHandler(Looper loop) {
        super(loop);
    }
}

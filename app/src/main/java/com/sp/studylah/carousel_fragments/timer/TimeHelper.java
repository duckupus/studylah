package com.sp.studylah.carousel_fragments.timer;

import java.io.Serializable;

public class TimeHelper implements Serializable {

    private int timeRemaining;
    private boolean isRunning;
    public TimeHelper(int timeRemaining) {
        this.timeRemaining = timeRemaining;
        this.isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }
    public boolean toggleState() {
        isRunning = !isRunning;
        return isRunning;
    }
    public String getTimeRemainingString() {
        String seconds = String.valueOf(timeRemaining % 60);
        if(seconds.length() < 2) seconds = "0" + seconds;
        String minutes = String.valueOf((timeRemaining / 60) % 60);
        if(minutes.length() < 2) minutes = "0" + minutes;
        String hours = String.valueOf((timeRemaining / 60) / 60);
        return hours + ":" + minutes + ":" + seconds;
    }
    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTime(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void tick() {
        timeRemaining--;
    }
}

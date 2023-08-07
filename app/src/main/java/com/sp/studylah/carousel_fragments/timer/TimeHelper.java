package com.sp.studylah.carousel_fragments.timer;

import java.io.Serializable;

public class TimeHelper implements Serializable {

    private long timeRemaining;
    private boolean isRunning;
    private long initialTime;
    public TimeHelper(long timeRemaining) {
        this.timeRemaining = timeRemaining;
        this.initialTime = timeRemaining;
        this.isRunning = true;
    }
    public float getProgress() {
        return (float) (initialTime - timeRemaining) / (float) initialTime;
    }

    public boolean isRunning() {
        return isRunning;
    }
    public boolean toggleState() {
        isRunning = !isRunning;
        return isRunning;
    }
    public String getTimeRemainingString() {
        int timeRemaining = (int) this.timeRemaining / 1000;
        String seconds = String.valueOf(timeRemaining % 60);
        if(seconds.length() < 2) seconds = "0" + seconds;
        String minutes = String.valueOf((timeRemaining / 60) % 60);
        if(minutes.length() < 2) minutes = "0" + minutes;
        String hours = String.valueOf((timeRemaining / 60) / 60);
        return hours + ":" + minutes + ":" + seconds;
    }
    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTime(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void tick() {
        timeRemaining--;
    }
}

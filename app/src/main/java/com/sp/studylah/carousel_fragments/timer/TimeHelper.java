package com.sp.studylah.carousel_fragments.timer;

import java.io.Serializable;

public class TimeHelper implements Serializable {

    public class TimeHelperHelper implements Serializable {
        public static final boolean UP = false;
        public static final boolean DOWN = true;
    }
    private int timeRemaining;
    private boolean direction;
    private boolean isRunning;

    public TimeHelper(int timeRemaining, boolean direction) {
        this.timeRemaining = timeRemaining;
        this.direction = direction;
        this.isRunning = true;
    }

    public boolean getCountType() {
        return direction;
    }
    public String getTimeRemainingString() {
        String seconds = String.valueOf(timeRemaining % 60);
        if(seconds.length() < 2) seconds = "0" + seconds;
        String minutes = String.valueOf((timeRemaining / 60) % 60);
        if(minutes.length() < 2) minutes = "0" + minutes;
        String hours = String.valueOf((timeRemaining / 60) / 60);
        return hours + ":" + minutes + ":" + seconds;
    }
    public boolean isRunning() {
        return isRunning;
    }
    public boolean stop() {
        isRunning = false;
        return isRunning;
    }
    public boolean cont() {
        isRunning = true;
        return isRunning;
    }
    public boolean toggle() {
        isRunning = !isRunning;
        return isRunning;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void addTime(int timeRemaining) {
        if (timeRemaining >= 0) {
            this.timeRemaining += timeRemaining;
        }
    }
    public void subtractTime(int timeRemaining) {
        if (timeRemaining >= 0) {
            this.timeRemaining -= timeRemaining;
        }
    }
    public int tick() {
        if(!isRunning) return timeRemaining;
        if (direction == TimeHelperHelper.DOWN) {
            return decrementTimeRemaining();
        } else {
            return incrementTimeRemaining();
        }
    }

    private int decrementTimeRemaining() {
        this.timeRemaining--;
        return timeRemaining;
    }
    private int incrementTimeRemaining() {
        this.timeRemaining++;
        return timeRemaining;
    }

    public int setTime(int time) {
        this.timeRemaining = time;
        return timeRemaining;
    }
}

package com.sp.studylah.carousel_fragments.timer;

public class TimerHelper {
    private int timeRemaining;

    public TimerHelper(int timeRemaining) {
        this.timeRemaining = timeRemaining;
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

    public void addTime(int timeRemaining) {
        if (timeRemaining >= 0) {
            this.timeRemaining = timeRemaining;
        }
    }

    public int decrementTimeRemaining() {
        if (timeRemaining >= 0) {
            this.timeRemaining--;
        }
        return timeRemaining;
    }
}

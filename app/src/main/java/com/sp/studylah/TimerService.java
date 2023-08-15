package com.sp.studylah;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.sp.studylah.Pages.view3;
import com.sp.studylah.carousel_fragments.timer.TimeHelper;

import java.util.concurrent.TimeUnit;

public class TimerService extends Service {
    public static final String CHANNEL_ID = "TimerServiceChannel";
    private Looper serviceLooper;
    /*
    private TimerServiceHandler timerServiceHandler;

    private final class TimerServiceHandler extends Handler {
        public TimerServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            stopSelf(msg.arg1);
        }
    } */
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments", 1);
        thread.start();
        serviceLooper = thread.getLooper();
        //timerServiceHandler = new TimerServiceHandler(serviceLooper);
    }

    private NotificationManager notificationManager;
    private CountDownTimer countDownTimer = null;
    private TimeHelper timeHelper;
    private NotificationCompat.Builder builder;
    private final String COUNTDOWN_BR = "com.sp.studylah.countdown_timer_update";
    private final String COUNTDOWN_BR_SERVICE = "com.sp.studylah.countdown_timer_update2";
    private BroadcastReceiver serviceBroadcastReceiver;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        assert intent.getExtras() != null;
        timeHelper = (TimeHelper) intent.getExtras().getSerializable("timeHelper");
        assert timeHelper != null;
        countDownTimer = new CountDownTimer(timeHelper.getTimeRemaining(), 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeHelper.setTime(millisUntilFinished);
                Intent intent = new Intent(COUNTDOWN_BR);
                intent.putExtra("timeHelper", timeHelper);
                intent.putExtra("countdown_timer_finished", false);
                sendBroadcast(intent);
                builder.setContentText("Time left: "+timeHelper.getTimeRemainingString());
                notificationManager.notify(1, builder.build());
            }


            @Override
            public void onFinish() {
                timeHelper.toggleState();
                Intent intent = new Intent(COUNTDOWN_BR);
                intent.putExtra("timeHelper", timeHelper);
                intent.putExtra("countdown_timer_finished", true);
                sendBroadcast(intent);
                stopSelf();
            }
        };
        serviceBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(COUNTDOWN_BR_SERVICE)) {
                    TimeHelper timeHelper1 = (TimeHelper) intent.getExtras().getSerializable("timeHelper");
                    boolean countdown_timer_finished = intent.getBooleanExtra("countdown_timer_finished", false);
                    assert timeHelper1 != null; //replaces timeHelper with provided timeHelper
                    timeHelper = timeHelper1;
                    if (countdown_timer_finished) {
                        stopSelf();
                    }
                    else if (timeHelper.isRunning()) {
                        if(countDownTimer != null)  {
                            countDownTimer.cancel();
                            countDownTimer = null;
                        }
                        countDownTimer = new CountDownTimer(timeHelper.getTimeRemaining(), 100) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                timeHelper.setTime(millisUntilFinished);
                                Intent intent = new Intent(COUNTDOWN_BR);
                                intent.putExtra("timeHelper", timeHelper);
                                intent.putExtra("countdown_timer_finished", false);
                                sendBroadcast(intent);
                                builder.setContentText("Time left: "+timeHelper.getTimeRemainingString());
                                notificationManager.notify(1, builder.build());
                            }

                            @Override
                            public void onFinish() {
                                timeHelper.toggleState();
                                Intent intent = new Intent(COUNTDOWN_BR);
                                intent.putExtra("timeHelper", timeHelper);
                                intent.putExtra("countdown_timer_finished", true);
                                sendBroadcast(intent);
                                stopSelf();
                            }

                        };
                        countDownTimer.start();
                    }
                    else if (!timeHelper.isRunning() && countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }
            }
        };
        registerReceiver(serviceBroadcastReceiver, new IntentFilter(COUNTDOWN_BR_SERVICE));
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, view3.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationIntent.putExtra("timeHelper", timeHelper);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Countdown Timer")
                .setContentText("Timer is running!")
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true);
        startForeground(1, builder.build());
        countDownTimer.start();

        /*
        Message msg = timerServiceHandler.obtainMessage();
        msg.arg1 = startId;
        timerServiceHandler.sendMessage(msg);
         */
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public  void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Time finished! Goodbye!", Toast.LENGTH_SHORT).show();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmSound);
        ringtone.play();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ringtone.stop();
            }
        }, 5000);
        unregisterReceiver(serviceBroadcastReceiver);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Timer Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }
}

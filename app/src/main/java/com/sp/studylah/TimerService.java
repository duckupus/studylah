package com.sp.studylah;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.sp.studylah.Pages.view3;
import com.sp.studylah.carousel_fragments.timer.TimeHelper;

public class TimerService extends Service {
    public static final String CHANNEL_ID = "TimerServiceChannel";
    private Looper serviceLooper;
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
    }
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments", 1);
        thread.start();
        serviceLooper = thread.getLooper();
        timerServiceHandler = new TimerServiceHandler(serviceLooper);
    }

    private NotificationManager notificationManager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        assert intent.getExtras() != null;
        TimeHelper timeHelper = (TimeHelper) intent.getExtras().getSerializable("timeHelper");
        assert timeHelper != null;

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, view3.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationIntent.putExtra("timeHelper", timeHelper);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Countdown Timer")
                .setContentText("TimerService is running!")
                .setContentIntent(pendingIntent);
        startForeground(1, builder.build());

        Message msg = timerServiceHandler.obtainMessage();
        msg.arg1 = startId;
        timerServiceHandler.sendMessage(msg);
        while(timeHelper.getProgress() > 0.0);
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
        Toast.makeText(this, "Stopping the strea- TimerService!", Toast.LENGTH_SHORT).show();
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

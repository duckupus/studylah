package com.sp.studylah;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class TimerService extends Service {
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
        Toast.makeText(this, "Starting the strea- TimerService!", Toast.LENGTH_SHORT).show();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("timer_service_channel", "TimerService", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "TimerService")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("TimerService")
                .setContentText("TimerService is running!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        startForeground(1, builder.build());

        Message msg = timerServiceHandler.obtainMessage();
        msg.arg1 = startId;
        timerServiceHandler.sendMessage(msg);
        return START_STICKY;
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
}

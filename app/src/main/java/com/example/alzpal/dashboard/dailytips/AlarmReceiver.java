package com.example.alzpal.dashboard.dailytips;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        scheduleDailyTipNotification(context);
        Log.d("AlarmReceiver", "Daily Tip Notification Scheduled.");
    }

    private void scheduleDailyTipNotification(Context context) {
        Intent notificationIntent = new Intent(context, DailyTipNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long triggerAtMillis = System.currentTimeMillis() + AlarmManager.INTERVAL_DAY;

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }
}

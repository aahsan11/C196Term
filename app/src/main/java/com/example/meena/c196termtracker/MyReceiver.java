package com.example.meena.c196termtracker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.v4.app.NotificationCompat.PRIORITY_DEFAULT;

public class MyReceiver extends BroadcastReceiver {

    static int notificationID;

    String channel_id="test";
    String channel_id2="test";
    String vars;
    String startalert;
    String assessmentalert;
    @Override
    public void onReceive(Context context,  Intent intent) {

        createNotificationChannel(context,channel_id);


        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        vars=preferences.getString("alert", "none3");


        SharedPreferences preferences2= PreferenceManager.getDefaultSharedPreferences(context);
        startalert=preferences2.getString("startalert", "none1");

        SharedPreferences preferences3= PreferenceManager.getDefaultSharedPreferences(context);
        assessmentalert=preferences3.getString("assessmentalert", "none");



  if(vars.equals("[]")){
      Toast.makeText(context, "No course is ending today", Toast.LENGTH_SHORT).show();
  }
  else
  {
      Notification n = new NotificationCompat.Builder(context, channel_id)
              .setSmallIcon(R.drawable.ic_launcher_foreground)
              .setContentText(vars)
              .setContentTitle("Following Courses are Ending today :").build();

      NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
      notificationManager.notify(notificationID++, n);

  }



        if (startalert.equals("[]")){
            Toast.makeText(context, "No course is starting today", Toast.LENGTH_SHORT).show();
        }

        else {
            Notification n2 = new NotificationCompat.Builder(context, channel_id2)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(startalert)
                    .setContentTitle("Following Courses are starting today :").build();

            NotificationManager notificationManager2 = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager2.notify(notificationID++, n2);

        }





        if(assessmentalert.equals("[]")){
            Toast.makeText(context, "No assessment is due today", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(assessmentalert)
                    .setContentTitle("Following assessments are due today :").build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);

        }





    }
    private void createNotificationChannel(Context context, String CHANNEL_ID) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}


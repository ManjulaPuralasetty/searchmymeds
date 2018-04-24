package com.drugapp;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    String t1 = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional 
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());


      //  ApplicationContant.notificationSharedPref(this, 1, remoteMessage.getNotification().getTitle() + ":" + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    //This method is only for generating push notification
    //It is same as we did in earlier posts 
    private void sendNotification(String Title, String messageBody) {




       /* Bitmap notificationLargeIconBitmap = BitmapFactory.decodeResource(
                this.getResources(),
                R.drawable.notiflogo);*/
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentTitle(getResources().getString(R.string.app_name));
        builder.setContentText(messageBody);
       // builder.setLargeIcon(notificationLargeIconBitmap);
        builder.setSmallIcon(R.drawable.map_icon);
        builder.setAutoCancel(true);
        builder.setSound(defaultSoundUri);

        // on swipe delete notification

        Intent resultIntent = new Intent(this,Login.class);
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        //tap notification
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, 0);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }
}
package com.boundaryproj.bottletaste.UtilFolder.PushFolder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.boundaryproj.bottletaste.MainFolder.ChatFolder.Act.ChatMainActivity;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.SplashActivity;
import com.google.firebase.messaging.RemoteMessage;



/**
 * Created by sungchanbong on 2016. 9. 5..
 */
public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService

 {
    private static final String TAG = "FirebaseMsgService";
     Intent intent;
     PendingIntent pendingIntent;
     Uri defaultSoundUri;
     NotificationCompat.Builder notificationBuilder;
     NotificationManager notificationManager;

     /*
     AQuery aq;
     Gson gson;
     Intent intent;
     PendingIntent pendingIntent;
     Uri defaultSoundUri;
     NotificationCompat.Builder notificationBuilder;
     NotificationManager notificationManager;
     */


     @Override
     public void onMessageReceived(RemoteMessage remoteMessage) {

         if(remoteMessage.getData().get("push_type").toString().equals("chat")){

             intent = new Intent(getApplicationContext(), ChatMainActivity.class);
             intent.putExtra("friend_id",remoteMessage.getData().get("user_id").toString());
             intent.putExtra("chat_code",remoteMessage.getData().get("code").toString());


             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(remoteMessage.getData().get("title"));
             notificationBuilder.setContentText(remoteMessage.getData().get("message"));
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0 , notificationBuilder.build());

         }else if(remoteMessage.getData().get("push_type").toString().equals("bounding")){

             intent = new Intent(getApplicationContext(), SplashActivity.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(remoteMessage.getData().get("title"));
             notificationBuilder.setContentText(remoteMessage.getData().get("message"));
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0 , notificationBuilder.build());

         }else if(remoteMessage.getData().get("push_type").toString().equals("friend")){
             intent = new Intent(getApplicationContext(), SplashActivity.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(remoteMessage.getData().get("title"));
             notificationBuilder.setContentText(remoteMessage.getData().get("message"));
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0 , notificationBuilder.build());
         }



         /*
         aq = new AQuery(this);
         gson = new Gson();
       // String title = remoteMessage.getNotification().getTitle();
       //  String message = remoteMessage.getNotification().getBody();
       //  go_page(title,message);
         Log.d("object",remoteMessage.getData().get("code"));
         Log.d("object",remoteMessage.getData().get("push_type"));
         go_page_update(remoteMessage.getData().get("code"),remoteMessage.getData().get("push_type"),remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
*/
     }
/*
     private void go_page_update(String code, final String type, String title, String message){
         if(type.toString().equals("storygood")){
             intent = new Intent(getApplicationContext(), DetailStory.class);
             intent.putExtra("code",code);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0 , notificationBuilder.build());


         }else if(type.toString().equals("storyreply")){

             intent = new Intent(getApplicationContext(), ReplyActivity.class);
             intent.putExtra("code",code);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0, notificationBuilder.build());

         }else if(type.toString().equals("meetinggood")){

             intent = new Intent(getApplicationContext(), MeetingDetail.class);
             intent.putExtra("code",code);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0, notificationBuilder.build());

         }else if(type.toString().equals("meetingreply")){

             intent = new Intent(getApplicationContext(), MeetingReply.class);
             intent.putExtra("code",code);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0, notificationBuilder.build());

         }else if(type.toString().equals("shopgood")){

             intent = new Intent(getApplicationContext(), AuctionDetail.class);
             intent.putExtra("code",code);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0 , notificationBuilder.build());

         }else if(type.toString().equals("shopreply")){

             intent = new Intent(getApplicationContext(), AuctionReply.class);
             intent.putExtra("code",code);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0 , notificationBuilder.build());

         }else  if(type.toString().equals("cluboption")){

             intent = new Intent(getApplicationContext(), ClubMainActivity.class);
             intent.putExtra("club_code",code);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0 , notificationBuilder.build());

         }else  if(type.toString().equals("clubout")){

             intent = new Intent(getApplicationContext(), HomeActivity.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);
             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             notificationBuilder = new NotificationCompat.Builder(this);
             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.notify(0 , notificationBuilder.build());

         }else if(type.toString().equals("message")){
             String temp_code[] = code.split("&kim&");
             intent = new Intent(getApplicationContext(), ChattingActivity.class);
             intent.putExtra("to_email",temp_code[1]);
             intent.putExtra("nickname",temp_code[2]);
             intent.putExtra("proimg",temp_code[3]);

             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);

             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

             notificationBuilder = new NotificationCompat.Builder(this);

             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

             notificationManager.notify(0 , notificationBuilder.build());


         }else{

             intent = new Intent(getApplicationContext(), HomeActivity.class);

             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                     | Intent.FLAG_ACTIVITY_CLEAR_TOP
                     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
             pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                     PendingIntent.FLAG_CANCEL_CURRENT);

             defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

             notificationBuilder = new NotificationCompat.Builder(this);

             notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
             notificationBuilder.setContentTitle(title);
             notificationBuilder.setContentText(message);
             notificationBuilder.setAutoCancel(true);
             notificationBuilder.setSound(defaultSoundUri);
             notificationBuilder.setContentIntent(pendingIntent);
             notificationManager =
                     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

             notificationManager.notify(0 , notificationBuilder.build());

         }

     }

     private void go_page(final String title, final String message){
         intent = new Intent(getApplicationContext(), HomeActivity.class);

         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                 | Intent.FLAG_ACTIVITY_CLEAR_TOP
                 | Intent.FLAG_ACTIVITY_SINGLE_TOP);
         pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                 PendingIntent.FLAG_CANCEL_CURRENT);

         defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

         notificationBuilder = new NotificationCompat.Builder(this);

         notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
         notificationBuilder.setContentTitle(title);
         notificationBuilder.setContentText(message);
         notificationBuilder.setAutoCancel(true);
         notificationBuilder.setSound(defaultSoundUri);
         notificationBuilder.setContentIntent(pendingIntent);
         notificationManager =
                 (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

         notificationManager.notify(0 , notificationBuilder.build());
     }


     private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             int color = 0xff0000;
             notificationBuilder.setColor(color);
             return R.drawable.pushicon;

         } else {
             return R.drawable.pushicon;
         }
     }
     */

     private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             int color = getResources().getColor(R.color.colorPrimary);
             notificationBuilder.setColor(color);
             return R.mipmap.ic_launcher;

         } else {
             return R.mipmap.ic_launcher;
         }
     }


}



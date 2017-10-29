package com.yst.sms.myservice;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Данный сервис эмулирует загрузку данных через прогресс и передает результат в активность MainActivity
 */
public class MyIntentService extends IntentService {

    public static final String PENDING_RESULT = "pending_result";
    public static final String RESULT = "result";
    public static final int RESULT_CODE = "countMsgs".hashCode();

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("happy", "intent service thread started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int id =10;
        ProgressNotificationCallback progress =  new ProgressNotificationCallback(this,id,"Загрузка","Идет загрузка данных");
     //   beginProgress();
       /// { фиктивная задержка начало
        Log.d("happy", "intent service thread "+Thread.currentThread().getId());


        int max =100;
        for(int i=0;i<max;i=i+5) {
            progress.onProgress(max,i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Log.d("happy", "intent service thread done "+Thread.currentThread().getId());

      progress.onComplete("OK");
        /// } фиктивная задержка конец

        Intent resultIntent = new Intent();

        // получили параметры от клиента
        int number=intent.getIntExtra("number",0);

        int result = number*number;
        resultIntent.putExtra(RESULT,result );

        // плучаем PendingIntent из активности
        PendingIntent pendingIntentFromActivity = intent.getParcelableExtra(  PENDING_RESULT);

        try {
            pendingIntentFromActivity.send(this, RESULT_CODE, resultIntent);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
       // notifyUser("for Vova",result);
    }

 /*
    private NotificationCompat.Builder builder;
    private NotificationManager nm;
    private int id, prev;


    private void notifyUser(String phoneNumber, int msgsCount) {
        String msg = String.format(
                "Found %d from the phone number %s", msgsCount, phoneNumber);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                        .setContentTitle("Inbox Counter")
                        .setContentText(msg);
        // Gets an instance of the NotificationManager service
        NotificationManager nm = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        // Sets an unique ID for this notification
        nm.notify(phoneNumber.hashCode(), builder.build());
    } */




    @Override
    public void onDestroy() {
        Log.d("happy", "intent service thread destroyed");
        super.onDestroy();
    }
}

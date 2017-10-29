package com.yst.sms.myservice;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by user on 28.10.2017.
 */
public class SecondIntentService extends IntentService {
    public static final String DIGEST_BROADCAST =
            "asynchronousandroid.chapter5.DIGEST_BROADCAST";
    public static final String RESULT = "result";


    public SecondIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("happy", "intent service thread started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int number=intent.getIntExtra("number",0);
        Intent intentToBroadcast = new Intent(DIGEST_BROADCAST);
        intentToBroadcast.putExtra(RESULT, number*number);
        sendBroadcast(intentToBroadcast);
    }


    @Override
    public void onDestroy() {
        Log.d("happy", "intent service thread destroyed");
        super.onDestroy();
    }
}

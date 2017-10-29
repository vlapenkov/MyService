package com.yst.sms.myservice;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver mMyBroadcastReceiver ;
    private static final int REQUEST_CODE = 0;
    TextView msgCountBut ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         msgCountBut = (TextView) findViewById(R.id.result);

        mMyBroadcastReceiver = new MyBroadcastReceiver();

        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(SecondIntentService.DIGEST_BROADCAST);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(mMyBroadcastReceiver, intFilt);
    }

    /*
1. Вызывает по кнопке MyIntentSErvice и получает результат в  onActivityResult
 */
    public void startIntentService(View view) {


        PendingIntent pendingIntent = createPendingResult(
                REQUEST_CODE, new Intent(), 0);

        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra(MyIntentService.PENDING_RESULT, pendingIntent); // pendingIntent - передается в IntentService
        intent.putExtra("number", 10);
        startService(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE && resultCode==MyIntentService.RESULT_CODE)
        {

            int result = data.getIntExtra(MyIntentService.RESULT, -1);
            // Update UI View with the result

            msgCountBut.setText(Integer.toString(result));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
    Второй IntentSErvice который запускает broadcast
     */
    public void startServiceWithBroadCast(View view) {
        Intent intent = new Intent(this, SecondIntentService.class);
        intent.putExtra("number", 10);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
    }

    /*
    BroadcastReceiver используется для получения результата
     */
    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra(SecondIntentService.RESULT, -1);
            msgCountBut.setText(Integer.toString(result));


        }

    }
}

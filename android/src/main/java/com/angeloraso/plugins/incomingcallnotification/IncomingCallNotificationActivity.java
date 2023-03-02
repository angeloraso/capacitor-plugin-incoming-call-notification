package com.angeloraso.plugins.incomingcallnotification;

import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.ANSWER_ACTION;
import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.CLICK_ACTION;
import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.DECLINE_ACTION;
import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.TERMINATE_ACTION;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class IncomingCallNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (IncomingCallNotificationService.callBack == null) {
            return;
        }

        if (CLICK_ACTION.equals(intent.getAction())) {
            IncomingCallNotificationService.callBack.onClick();
        } else if (DECLINE_ACTION.equals(intent.getAction())) {
            IncomingCallNotificationService.callBack.onDecline();
        } else if (ANSWER_ACTION.equals(intent.getAction())) {
            IncomingCallNotificationService.callBack.onAnswer();
        } else if (TERMINATE_ACTION.equals(intent.getAction())) {
            IncomingCallNotificationService.callBack.onTerminate();
        }

        finish();
    }
}

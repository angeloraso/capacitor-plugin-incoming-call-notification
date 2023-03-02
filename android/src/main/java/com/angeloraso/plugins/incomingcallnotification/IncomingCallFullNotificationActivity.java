package com.angeloraso.plugins.incomingcallnotification;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class IncomingCallFullNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setShowWhenLocked(true);
        setTurnScreenOn(true);

        if (IncomingCallNotificationService.callBack == null) {
            return;
        }

        IncomingCallNotificationService.callBack.onClick();
        finish();
    }
}

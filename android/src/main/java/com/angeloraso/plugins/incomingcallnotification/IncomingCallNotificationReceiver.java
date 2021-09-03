package com.angeloraso.plugins.incomingcallnotification;

import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.ANSWER_ACTION;
import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.CLICK_ACTION;
import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.DECLINE_ACTION;
import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.TERMINATE_ACTION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IncomingCallNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (IncomingCallNotificationService.callBack == null) {
            return;
        }

        if (CLICK_ACTION.equals(intent.getAction())){
            IncomingCallNotificationService.callBack.onClick();
        } else if (DECLINE_ACTION.equals(intent.getAction())){
            IncomingCallNotificationService.callBack.onDecline();
        } else if (ANSWER_ACTION.equals(intent.getAction())){
            IncomingCallNotificationService.callBack.onAnswer();
        } else if (TERMINATE_ACTION.equals(intent.getAction())){
            IncomingCallNotificationService.callBack.onTerminate();
        }
    }
}
package com.angeloraso.plugins.incomingcallnotification;

public interface IncomingCallNotificationListener {
    void onClick();
    void onDecline();
    void onAnswer();
    void onTerminate();
}

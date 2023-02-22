package com.angeloraso.plugins.incomingcallnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;

public class IncomingCallNotificationService extends Service {

    public static String CLICK_ACTION = "click_incoming_call_notification";
    public static String DECLINE_ACTION = "decline_incoming_call";
    public static String ANSWER_ACTION = "answer_incoming_call";
    public static String TERMINATE_ACTION = "terminate_current_call";

    public static final int NOTIFICATION_ID = -574543923;

    private final IBinder mLocalBinder = new LocalBinder();
    public static CallBack callBack;

    private IncomingCallNotificationSettings mSettings;

    public IncomingCallNotificationService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    class LocalBinder extends Binder {

        IncomingCallNotificationService getService() {
            return IncomingCallNotificationService.this;
        }
    }

    public interface CallBack {
        void onClick();
        void onDecline();
        void onAnswer();
        void onTerminate();
    }

    public void setCallBack(CallBack callBack) {
        IncomingCallNotificationService.callBack = callBack;
    }

    public void setSettings(IncomingCallNotificationSettings settings) {
        this.mSettings = settings;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSettings = new IncomingCallNotificationSettings();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSettings = null;
        stopForeground(true);
        getNotificationManager().cancel(NOTIFICATION_ID);
    }

    /**
     * START_NOT_STICKY: if the process (the App) is killed with no remaining start commands to deliver,
     * then the service will be stopped instead of restarted
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    public void createNotification() {
        RemoteViews customView;
        Resources res = getResources();
        String pkgName = getPackageName();
        IncomingCallNotificationActivity.mSettings = mSettings;
        final Intent notificationIntent = new Intent(getApplicationContext(), IncomingCallNotificationActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(
            getApplicationContext(),
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        int pictureResource = res.getIdentifier(mSettings.getPicture(), "drawable", pkgName);
        Boolean thereIsACallInProgress = mSettings.getThereIsACallInProgress();
        if (!thereIsACallInProgress) {
            customView = new RemoteViews(pkgName, R.layout.first_incoming_call);
            customView.setImageViewResource(R.id.firstCallPictureId, pictureResource);
            customView.setTextViewText(R.id.firstCallerNameId, mSettings.getCallerName());
            customView.setTextViewText(R.id.firstCallerNumberId, mSettings.getCallerNumber());
            customView.setOnClickPendingIntent(R.id.declineButtonId, getPendingIntent(DECLINE_ACTION));
            customView.setTextViewText(R.id.declineButtonTextId, mSettings.getDeclineButtonText());
            customView.setOnClickPendingIntent(R.id.answerButtonId, getPendingIntent(ANSWER_ACTION));
            customView.setTextViewText(R.id.answerButtonTextId, mSettings.getAnswerButtonText());
        } else {
            customView = new RemoteViews(pkgName, R.layout.second_incoming_call);
            customView.setImageViewResource(R.id.secondCallPictureId, pictureResource);
            customView.setTextViewText(R.id.secondCallerNameId, mSettings.getCallerName());
            customView.setTextViewText(R.id.secondCallerNumberId, mSettings.getCallerNumber());
            customView.setOnClickPendingIntent(R.id.terminateAndAnswerFrontButtonId, getPendingIntent(TERMINATE_ACTION));
            customView.setTextViewText(R.id.terminateAndAnswerButtonTextId, mSettings.getTerminateAndAnswerButtonText());
            customView.setOnClickPendingIntent(R.id.declineSecondCallButtonId, getPendingIntent(DECLINE_ACTION));
            customView.setTextViewText(R.id.declineSecondCallButtonTextId, mSettings.getDeclineSecondCallButtonText());
            customView.setOnClickPendingIntent(R.id.holdAndAnswerFrontButtonId, getPendingIntent(ANSWER_ACTION));
            customView.setTextViewText(R.id.holdAndAnswerButtonTextId, mSettings.getHoldAndAnswerButtonText());
        }

        final String CHANNEL_ID = "incoming-call-notification-channel-id";
        final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
        final long[] DEFAULT_VIBRATE_PATTERN = { 0, 250, 250, 250 };
        final NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, mSettings.getChannelName(), CHANNEL_IMPORTANCE);
        notificationChannel.setDescription(mSettings.getChannelDescription());
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(DEFAULT_VIBRATE_PATTERN);
        notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, null);
        // Register the channel with the system; you can't change the importance or other notification behaviors after this
        getNotificationManager().createNotificationChannel(notificationChannel);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(mSettings.getChannelName())
            .setContentText(mSettings.getCallerName() + " - " + mSettings.getCallerNumber())
            .setTicker(mSettings.getChannelName())
            .setSmallIcon(R.drawable.answer_24)
            .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
            // To know if it is necessary to disturb the user with a notification despite having activated the "Do not interrupt" mode
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            // VISIBILITY_PUBLIC displays the full content of the notification
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setAutoCancel(false)
            // The notification is assigned PRIORITY_MAX to show as a popup for api 25 and earlier
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCustomContentView(customView)
            .setCustomBigContentView(customView)
            .setContentIntent(getPendingIntent(CLICK_ACTION))
            .setFullScreenIntent(pendingIntent, true);

        startForeground(NOTIFICATION_ID, notification.build());
    }

    private PendingIntent getPendingIntent(String action) {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, IncomingCallNotificationReceiver.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    /**
     * Returns the shared notification service manager.
     */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
}

package com.angeloraso.plugins.incomingcallnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.text.Html;
import android.widget.RemoteViews;

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

        int pictureResource = res.getIdentifier(mSettings.getPicture(), "drawable", pkgName);

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

        Notification.Builder notificationBuilder = new Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(mSettings.getChannelName())
            // Ongoing notifications cannot be dismissed by the user
            .setOngoing(true)
            // Set the "ticker" text which is sent to accessibility services.
            .setTicker(mSettings.getChannelName())
            // To know if it is necessary to disturb the user with a notification despite having activated the "Do not interrupt" mode
            .setCategory(Notification.CATEGORY_CALL)
            // Add a timestamp pertaining to the notification
            .setWhen(System.currentTimeMillis())
            // VISIBILITY_PUBLIC displays the full content of the notification
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            // Make this notification automatically dismissed when the user touches it.
            .setAutoCancel(false)
            .setColor(Color.parseColor(mSettings.getColor()))
            // Set whether or not this notification should not bridge to other devices.
            .setLocalOnly(true)
            .setFullScreenIntent(getPendingIntent(CLICK_ACTION), true);

        Boolean thereIsACallInProgress = mSettings.getThereIsACallInProgress();

        // Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !thereIsACallInProgress) {
            Icon icon = Icon.createWithResource(this, pictureResource);
            Person caller = new Person.Builder()
                .setIcon(icon)
                .setName(mSettings.getCallerName() + " - " + mSettings.getCallerNumber())
                .setImportant(true)
                .build();

            Notification.CallStyle notificationStyle;
            notificationStyle =
                Notification.CallStyle.forIncomingCall(caller, getPendingIntent(DECLINE_ACTION), getPendingIntent(ANSWER_ACTION));

            notificationBuilder.setStyle((notificationStyle));
            notificationBuilder.setSmallIcon(R.drawable.answer_24);
            notificationBuilder.setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE);
        } else {
            notificationBuilder.setSmallIcon(pictureResource);
            notificationBuilder.setContentIntent(getPendingIntent(CLICK_ACTION));
            notificationBuilder.setContentText(mSettings.getCallerName() + " - " + mSettings.getCallerNumber());

            if (thereIsACallInProgress) {
                Notification.Action answerAction = new Notification.Action.Builder(
                    Icon.createWithResource(this, R.drawable.hold_and_answer_24),
                    Html.fromHtml(
                        "<font color=\"" +
                        Color.parseColor(mSettings.getHoldAndAnswerButtonColor()) +
                        "\">" +
                        mSettings.getHoldAndAnswerButtonText() +
                        "</font>",
                        Html.FROM_HTML_MODE_LEGACY
                    ),
                    getPendingIntent(ANSWER_ACTION)
                )
                    .build();

                Notification.Action declineAction = new Notification.Action.Builder(
                    Icon.createWithResource(this, R.drawable.decline_24),
                    Html.fromHtml(
                        "<font color=\"" +
                        Color.parseColor(mSettings.getDeclineSecondCallButtonColor()) +
                        "\">" +
                        mSettings.getDeclineSecondCallButtonText() +
                        "</font>",
                        Html.FROM_HTML_MODE_LEGACY
                    ),
                    getPendingIntent(DECLINE_ACTION)
                )
                    .build();

                Notification.Action terminateAction = new Notification.Action.Builder(
                    Icon.createWithResource(this, R.drawable.terminate_and_answer_24),
                    Html.fromHtml(
                        "<font color=\"" +
                        Color.parseColor(mSettings.getTerminateAndAnswerButtonColor()) +
                        "\">" +
                        mSettings.getTerminateAndAnswerButtonText() +
                        "</font>",
                        Html.FROM_HTML_MODE_LEGACY
                    ),
                    getPendingIntent(TERMINATE_ACTION)
                )
                    .build();
                notificationBuilder.setActions(terminateAction, declineAction, answerAction);
            } else {
                Notification.Action answerAction = new Notification.Action.Builder(
                    Icon.createWithResource(this, R.drawable.answer_24),
                    Html.fromHtml(
                        "<font color=\"" +
                        Color.parseColor(mSettings.getAnswerButtonColor()) +
                        "\">" +
                        mSettings.getAnswerButtonText() +
                        "</font>",
                        Html.FROM_HTML_MODE_LEGACY
                    ),
                    getPendingIntent(ANSWER_ACTION)
                )
                    .build();

                Notification.Action declineAction = new Notification.Action.Builder(
                    Icon.createWithResource(this, R.drawable.decline_24),
                    Html.fromHtml(
                        "<font color=\"" +
                        Color.parseColor(mSettings.getDeclineButtonColor()) +
                        "\">" +
                        mSettings.getDeclineButtonText() +
                        "</font>",
                        Html.FROM_HTML_MODE_LEGACY
                    ),
                    getPendingIntent(DECLINE_ACTION)
                )
                    .build();

                notificationBuilder.setActions(declineAction, answerAction);
            }
        }

        Notification notification = notificationBuilder.build();
        startForeground(NOTIFICATION_ID, notification);
    }

    private PendingIntent getPendingIntent(String action) {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, IncomingCallNotificationReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

package com.angeloraso.plugins.incomingcallnotification;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.appcompat.app.AppCompatActivity;

public class IncomingCallNotification implements IncomingCallNotificationService.CallBack {

    private Context context;
    private AppCompatActivity activity;
    private IncomingCallNotificationSettings mSettings;
    private IncomingCallNotificationListener mListener;
    private IncomingCallNotificationService incomingCallNotificationService;
    private Boolean mIsBound = false;

    IncomingCallNotification(final AppCompatActivity activity, final Context context) {
        this.activity = activity;
        this.context = context;
        mSettings = new IncomingCallNotificationSettings();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            incomingCallNotificationService = ((IncomingCallNotificationService.LocalBinder) iBinder).getService();
            incomingCallNotificationService.setCallBack(IncomingCallNotification.this);
            incomingCallNotificationService.setSettings(mSettings);
            incomingCallNotificationService.createNotification();
        }

        public void onServiceDisconnected(ComponentName className) {
            incomingCallNotificationService = null;
        }
    };

    @Override
    public void onClick() {
        mListener.onClick();
        openApp();
    }

    @Override
    public void onDecline() {
        mListener.onDecline();
    }

    @Override
    public void onAnswer() {
        mListener.onAnswer();
        openApp();
    }

    @Override
    public void onTerminate() {
        mListener.onTerminate();
        openApp();
    }

    /**
     * Show the incoming call notification
     *
     * @param settings Settings used to show the incoming call notification
     * @param listener A listener to handle user actions
     */
    public void show(final IncomingCallNotificationSettings settings, final IncomingCallNotificationListener listener) {
        this.mSettings = settings;
        this.mListener = listener;
        startService();
    }

    public void hide() {
        if (IncomingCallNotificationActivity.that != null) {
            IncomingCallNotificationActivity.that.finish();
        }
        stopService();
    }

    public void onResume() {
        hide();
    }

    private void startService() {
        if (mIsBound) {
            return;
        }

        Intent intent = new Intent(context, IncomingCallNotificationService.class);

        try {
            mIsBound = context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            context.startForegroundService(intent);
        } catch (Exception e) {}
    }

    private void stopService() {
        if (!mIsBound) {
            return;
        }

        Intent intent = new Intent(context, IncomingCallNotificationService.class);
        context.unbindService(mConnection);
        context.stopService(intent);
        mIsBound = false;
    }

    private void openApp() {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        activity.startActivity(intent);
    }
}

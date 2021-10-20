package com.angeloraso.plugins.incomingcallnotification;

import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.ANSWER_ACTION;
import static com.angeloraso.plugins.incomingcallnotification.IncomingCallNotificationService.DECLINE_ACTION;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class IncomingCallNotificationActivity extends AppCompatActivity {

    public static IncomingCallNotificationSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming_call_notification);
        setShowWhenLocked(true);
        setTurnScreenOn(true);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        config();
    }

    private void config() {
        Resources res = getResources();
        String pkgName = getPackageName();
        int pictureResource = res.getIdentifier(mSettings.getPicture(), "drawable", pkgName);

        ImageView logoView = (ImageView) findViewById(R.id.firstCallPictureId);
        logoView.setImageResource(pictureResource);

        TextView callerNameView = (TextView) findViewById(R.id.firstCallerNameId);
        callerNameView.setText(mSettings.getCallerName());

        TextView callerNumberView = (TextView) findViewById(R.id.firstCallerNameId);
        callerNumberView.setText(mSettings.getCallerNumber());

        TextView declineButtonView = (TextView) findViewById(R.id.declineButtonTextId);
        declineButtonView.setText(mSettings.getDeclineButtonText());
        Button declineButton = (Button) findViewById(R.id.declineButtonId);
        declineButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    action(DECLINE_ACTION);
                }
            }
        );

        TextView answerButtonView = (TextView) findViewById(R.id.answerButtonTextId);
        answerButtonView.setText(mSettings.getDeclineButtonText());
        Button answerButton = (Button) findViewById(R.id.answerButtonId);
        answerButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    action(ANSWER_ACTION);
                }
            }
        );
    }

    private void action(String action) {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, IncomingCallNotificationReceiver.class);
        intent.setAction(action);
        sendBroadcast(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        action(DECLINE_ACTION);
        super.onBackPressed();
    }
}

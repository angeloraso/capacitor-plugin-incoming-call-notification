package com.angeloraso.plugins.incomingcallnotification;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "IncomingCallNotification")
public class IncomingCallNotificationPlugin extends Plugin {

    private IncomingCallNotification incomingCallNotification;

    public void load() {
        AppCompatActivity activity = getActivity();
        Context context = getContext();
        incomingCallNotification = new IncomingCallNotification(activity, context);
    }

    @PluginMethod
    public void show(PluginCall call) {
        if (getActivity().isFinishing()) {
            call.reject("Incoming call notification plugin error: App is finishing");
            return;
        }

        IncomingCallNotificationSettings settings = getSettings(call);
        incomingCallNotification.show(
            settings,
            new IncomingCallNotificationListener() {
                @Override
                public void onClick() {
                    JSObject res = new JSObject();
                    res.put("response", "click");
                    call.resolve(res);
                }

                @Override
                public void onDecline() {
                    JSObject res = new JSObject();
                    res.put("response", "decline");
                    call.resolve(res);
                }

                @Override
                public void onAnswer() {
                    JSObject res = new JSObject();
                    res.put("response", "answer");
                    call.resolve(res);
                }

                @Override
                public void onTerminate() {
                    JSObject res = new JSObject();
                    res.put("response", "terminate");
                    call.resolve(res);
                }
            }
        );
    }

    @PluginMethod
    public void hide(PluginCall call) {
        incomingCallNotification.hide();
    }

    private IncomingCallNotificationSettings getSettings(PluginCall call) {
        IncomingCallNotificationSettings settings = new IncomingCallNotificationSettings();
        if (call.hasOption("callerName")) {
            settings.setCallerName((call.getString("callerName")));
        }
        if (call.hasOption("callerNumber")) {
            settings.setCallerNumber((call.getString("callerNumber")));
        }
        if (call.hasOption("picture")) {
            settings.setPicture((call.getString("picture")));
        }
        if (call.hasOption("thereIsACallInProgress")) {
            settings.setThereIsACallInProgress((call.getBoolean("thereIsACallInProgress")));
        }
        if (call.hasOption("declineButtonText")) {
            settings.setDeclineButtonText((call.getString("declineButtonText")));
        }
        if (call.hasOption("answerButtonText")) {
            settings.setAnswerButtonText((call.getString("answerButtonText")));
        }
        if (call.hasOption("terminateAndAnswerButtonText")) {
            settings.setTerminateAndAnswerButtonText((call.getString("terminateAndAnswerButtonText")));
        }
        if (call.hasOption("declineSecondCallButtonText")) {
            settings.setDeclineSecondCallButtonText((call.getString("declineSecondCallButtonText")));
        }
        if (call.hasOption("holdAndAnswerButtonText")) {
            settings.setHoldAndAnswerButtonText((call.getString("holdAndAnswerButtonText")));
        }
        if (call.hasOption("declineButtonColor")) {
            settings.setDeclineButtonColor((call.getString("declineButtonColor")));
        }
        if (call.hasOption("answerButtonColor")) {
            settings.setAnswerButtonColor((call.getString("answerButtonColor")));
        }
        if (call.hasOption("terminateAndAnswerButtonColor")) {
            settings.setTerminateAndAnswerButtonColor((call.getString("terminateAndAnswerButtonColor")));
        }
        if (call.hasOption("declineSecondCallButtonColor")) {
            settings.setDeclineSecondCallButtonColor((call.getString("declineSecondCallButtonColor")));
        }
        if (call.hasOption("holdAndAnswerButtonColor")) {
            settings.setHoldAndAnswerButtonColor((call.getString("holdAndAnswerButtonColor")));
        }
        if (call.hasOption("color")) {
            settings.setColor((call.getString("color")));
        }
        if (call.hasOption("channelName")) {
            settings.setChannelName((call.getString("channelName")));
        }
        if (call.hasOption("channelDescription")) {
            settings.setChannelDescription((call.getString("channelDescription")));
        }

        return settings;
    }

    /**
     * Called when the activity will start interacting with the user.
     */
    @Override
    public void handleOnResume() {
        incomingCallNotification.onResume();
    }

    /**
     * Called when the activity will be destroyed.
     */
    @Override
    public void handleOnDestroy() {
        incomingCallNotification.onDestroy();
    }
}

package com.angeloraso.plugins.incomingcallnotification;

public class IncomingCallNotificationSettings {
    private String callerName = "Unknown";
    private String callerNumber = "Unknown";
    private String picture = "picture";
    private Boolean thereIsACallInProgress = false;
    private String declineButtonText = "Decline";
    private String answerButtonText = "Answer";
    private String terminateAndAnswerButtonText = "Terminate & answer";
    private String declineSecondCallButtonText = "Decline";
    private String holdAndAnswerButtonText = "Hold & answer";
    private String channelName = "incoming-call-notification";
    private String channelDescription = "Incoming call notification";

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCallerNumber() {
        return callerNumber;
    }

    public void setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getThereIsACallInProgress() {
        return thereIsACallInProgress;
    }

    public void setThereIsACallInProgress(Boolean thereIsACallInProgress) {
        this.thereIsACallInProgress = thereIsACallInProgress;
    }

    public String getDeclineButtonText() {
        return declineButtonText;
    }

    public void setDeclineButtonText(String declineButtonText) {
        this.declineButtonText = declineButtonText;
    }

    public String getAnswerButtonText() {
        return answerButtonText;
    }

    public void setAnswerButtonText(String answerButtonText) {
        this.answerButtonText = answerButtonText;
    }

    public String getTerminateAndAnswerButtonText() {
        return terminateAndAnswerButtonText;
    }

    public void setTerminateAndAnswerButtonText(String terminateAndAnswerButtonText) {
        this.terminateAndAnswerButtonText = terminateAndAnswerButtonText;
    }

    public String getDeclineSecondCallButtonText() {
        return declineSecondCallButtonText;
    }

    public void setDeclineSecondCallButtonText(String declineSecondCallButtonText) {
        this.declineSecondCallButtonText = declineSecondCallButtonText;
    }

    public String getHoldAndAnswerButtonText() {
        return holdAndAnswerButtonText;
    }

    public void setHoldAndAnswerButtonText(String holdAndAnswerButtonText) {
        this.holdAndAnswerButtonText = holdAndAnswerButtonText;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }
}

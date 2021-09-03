export interface IncomingCallNotificationSettings {
    callerName: string;
    callerNumber: string;
    picture: string;
    thereIsACallInProgress: boolean;
    declineButtonText: string;
    answerButtonText: string;
    terminateAndAnswerButtonText: string;
    declineSecondCallButtonText: string;
    holdAndAnswerButtonText: string;
    channelName: string;
    channelDescription: string;
}
export interface IncomingCallNotificationPlugin {
    show(data?: Partial<IncomingCallNotificationSettings>): Promise<{
        response: 'click' | 'answer' | 'decline' | 'terminate';
    }>;
    hide(): Promise<void>;
}

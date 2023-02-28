export interface IncomingCallNotificationSettings {
  callerName: string;
  callerNumber: string;
  picture: string;
  thereIsACallInProgress: boolean;
  declineButtonText: string;
  declineButtonColor: string;
  answerButtonText: string;
  answerButtonColor: string;
  terminateAndAnswerButtonText: string;
  terminateAndAnswerButtonColor: string;
  declineSecondCallButtonText: string;
  declineSecondCallButtonColor: string;
  holdAndAnswerButtonText: string;
  holdAndAnswerButtonColor: string;
  channelName: string;
  channelDescription: string;
}

export interface IncomingCallNotificationPlugin {
  show(data?: Partial<IncomingCallNotificationSettings>): Promise<{ response: 'click' | 'answer' | 'decline' | 'terminate' }>;
  hide(): Promise<void>;
}

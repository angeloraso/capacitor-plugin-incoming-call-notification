import { WebPlugin } from '@capacitor/core';
import type { IncomingCallNotificationSettings, IncomingCallNotificationPlugin } from './definitions';
export declare class IncomingCallNotificationWeb extends WebPlugin implements IncomingCallNotificationPlugin {
    show(_data?: Partial<IncomingCallNotificationSettings>): Promise<{
        response: 'click' | 'answer' | 'decline' | 'terminate';
    }>;
    hide(): Promise<void>;
}

import { WebPlugin } from '@capacitor/core';

import type { IncomingCallNotificationSettings , IncomingCallNotificationPlugin } from './definitions';
/* eslint-disable @typescript-eslint/no-unused-vars */
export class IncomingCallNotificationWeb
  extends WebPlugin
  implements IncomingCallNotificationPlugin {
    async show(_data?: Partial<IncomingCallNotificationSettings>): Promise<{ response: 'click' | 'answer' | 'decline' | 'terminate' }> {
      throw this.unimplemented('Not implemented on web.');
    }  
    async hide(): Promise<void> {
      throw this.unimplemented('Not implemented on web.');
    }
}

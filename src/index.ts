import { registerPlugin } from '@capacitor/core';

import type { IncomingCallNotificationPlugin } from './definitions';

const IncomingCallNotification = registerPlugin<IncomingCallNotificationPlugin>(
  'IncomingCallNotification',
  {
    web: () => import('./web').then(m => new m.IncomingCallNotificationWeb()),
  },
);

export * from './definitions';
export { IncomingCallNotification };

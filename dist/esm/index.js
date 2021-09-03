import { registerPlugin } from '@capacitor/core';
const IncomingCallNotification = registerPlugin('IncomingCallNotification', {
    web: () => import('./web').then(m => new m.IncomingCallNotificationWeb()),
});
export * from './definitions';
export { IncomingCallNotification };
//# sourceMappingURL=index.js.map
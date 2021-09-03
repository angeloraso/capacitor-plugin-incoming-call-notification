import { WebPlugin } from '@capacitor/core';
/* eslint-disable @typescript-eslint/no-unused-vars */
export class IncomingCallNotificationWeb extends WebPlugin {
    async show(_data) {
        throw this.unimplemented('Not implemented on web.');
    }
    async hide() {
        throw this.unimplemented('Not implemented on web.');
    }
}
//# sourceMappingURL=web.js.map
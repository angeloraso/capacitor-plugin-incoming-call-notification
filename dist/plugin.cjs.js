'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const IncomingCallNotification = core.registerPlugin('IncomingCallNotification', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.IncomingCallNotificationWeb()),
});

/* eslint-disable @typescript-eslint/no-unused-vars */
class IncomingCallNotificationWeb extends core.WebPlugin {
    async show(_data) {
        throw this.unimplemented('Not implemented on web.');
    }
    async hide() {
        throw this.unimplemented('Not implemented on web.');
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    IncomingCallNotificationWeb: IncomingCallNotificationWeb
});

exports.IncomingCallNotification = IncomingCallNotification;
//# sourceMappingURL=plugin.cjs.js.map

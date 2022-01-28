# capacitor-plugin-incoming-call-notification

Plugin to create an incoming call notification

## Install

```bash
npm install capacitor-plugin-incoming-call-notification
npx cap sync
```

## API

<docgen-index>

* [`show(...)`](#show)
* [`hide()`](#hide)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### show(...)

```typescript
show(data?: Partial<IncomingCallNotificationSettings> | undefined) => Promise<{ response: 'click' | 'answer' | 'decline' | 'terminate'; }>
```

| Param      | Type                                                                                                                                |
| ---------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| **`data`** | <code><a href="#partial">Partial</a>&lt;<a href="#incomingcallnotificationsettings">IncomingCallNotificationSettings</a>&gt;</code> |

**Returns:** <code>Promise&lt;{ response: 'click' | 'answer' | 'decline' | 'terminate'; }&gt;</code>

--------------------


### hide()

```typescript
hide() => Promise<void>
```

--------------------


### Interfaces


#### IncomingCallNotificationSettings

| Prop                               | Type                 |
| ---------------------------------- | -------------------- |
| **`callerName`**                   | <code>string</code>  |
| **`callerNumber`**                 | <code>string</code>  |
| **`picture`**                      | <code>string</code>  |
| **`thereIsACallInProgress`**       | <code>boolean</code> |
| **`declineButtonText`**            | <code>string</code>  |
| **`answerButtonText`**             | <code>string</code>  |
| **`terminateAndAnswerButtonText`** | <code>string</code>  |
| **`declineSecondCallButtonText`**  | <code>string</code>  |
| **`holdAndAnswerButtonText`**      | <code>string</code>  |
| **`channelName`**                  | <code>string</code>  |
| **`channelDescription`**           | <code>string</code>  |


### Type Aliases


#### Partial

Make all properties in T optional

<code>{ [P in keyof T]?: T[P]; }</code>

</docgen-api>

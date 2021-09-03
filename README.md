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

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### show(...)

```typescript
show(data?: any) => any
```

| Param      | Type             |
| ---------- | ---------------- |
| **`data`** | <code>any</code> |

**Returns:** <code>any</code>

--------------------


### hide()

```typescript
hide() => any
```

**Returns:** <code>any</code>

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

</docgen-api>

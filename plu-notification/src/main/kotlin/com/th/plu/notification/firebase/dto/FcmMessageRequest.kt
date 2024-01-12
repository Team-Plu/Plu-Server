package com.th.plu.notification.firebase.dto

class FcmMessageRequest(val validateOnly: Boolean, val message: Message) {

    class Message(val notification: Notification, val android: Android, val apns: Apns, val token: String)
    class Notification(val title: String, val body: String)
    class Data(val title: String, val body: String)
    class Android(val data: Data)
    class Apns(val payload: Payload)
    class Payload(val aps: Aps)
    class Aps(val alert: Alert)
    class Alert(val title: String, val body: String)

    companion object {
        fun of(fcmToken: String, title: String, body: String): FcmMessageRequest {
            val notification = Notification(title, body)
            val android = Android(Data(title, body))
            val apns = Apns(Payload(Aps(Alert(title, body))))
            val message = Message(notification, android, apns, fcmToken)

            return FcmMessageRequest(false, message)
        }
    }
}

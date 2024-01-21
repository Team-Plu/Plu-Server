package com.th.plu.notification.firebase.dto

data class FcmMessageRequest(val validateOnly: Boolean, val message: Message) {

    class Message(val token: String, val notification: Notification, val android: Android, val apns: Apns)
    class Notification(val title: String, val body: String)
    class Android()
    class Apns()

    companion object {
        fun of(fcmToken: String, title: String, body: String): FcmMessageRequest {
            val notification = Notification(title, body)
            val message = Message(fcmToken, notification, Android(), Apns())

            return FcmMessageRequest(false, message)
        }
    }
}

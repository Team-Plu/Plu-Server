package com.th.plu.external.firebase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.th.plu.external.sqs.dto.FirebaseMessageDto
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class FirebaseCloudMessageService(private val firebaseMessaging: FirebaseMessaging) {

    fun sendMessage(firebaseMessageDto: FirebaseMessageDto) {
        val notification = Notification(firebaseMessageDto.title, firebaseMessageDto.body)
        val message = Message.builder()
            .setToken(firebaseMessageDto.fcmToken)
            .setNotification(notification)
            .build()

        try {
            firebaseMessaging.send(message)
        } catch (exception: FirebaseMessagingException) {
            throw IllegalArgumentException("알림 전송 실패 [fcmToken=${firebaseMessageDto.fcmToken}]")
        }
    }

}

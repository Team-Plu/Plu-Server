package com.th.plu.external.sqs.dto

class FirebaseMessageDto(type: MessageType, val fcmToken: String, val title: String, val body: String): SqsMessageDto(type) {
}

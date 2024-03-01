package com.th.plu.external.sqs.dto

data class FirebaseMessageDto(val type: MessageType, val fcmToken: String?, val title: String, val body: String) :
    SqsMessageDto(type) {
}

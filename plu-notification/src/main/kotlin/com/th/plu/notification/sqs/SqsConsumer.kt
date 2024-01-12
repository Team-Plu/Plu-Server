package com.th.plu.notification.sqs

import com.fasterxml.jackson.databind.ObjectMapper
import com.th.plu.notification.firebase.FirebaseCloudMessageService
import com.th.plu.external.sqs.dto.FirebaseMessageDto
import com.th.plu.external.sqs.dto.MessageType
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class SqsConsumer(
    private val objectMapper: ObjectMapper,
    private val FirebaseCloudMessageService: FirebaseCloudMessageService
) {
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val SQS_CONSUME_LOG_MESSAE = "====> [SQS Queue Response]\n" + "info: %s\n" + "header: %s\n";

    private val MESSAGE_TYPE_HEADER = "type"

    @SqsListener("pluNotification.fifo")
    fun consume(@Payload info: String, @Headers headers: Map<String, String>) {
        try {
            log.info(String.format(SQS_CONSUME_LOG_MESSAE, info, headers))
            val messageType = headers.get(MESSAGE_TYPE_HEADER)
            when (messageType) {
                MessageType.FIREBASE.name -> {
                    val firebaseMessageDto = objectMapper.readValue(info, FirebaseMessageDto::class.java)
                    FirebaseCloudMessageService.sendMessageTo(firebaseMessageDto.fcmToken, firebaseMessageDto.title, firebaseMessageDto.body)
                }
                else ->
                    throw IllegalArgumentException("존재하지 않는 MessageType(${headers.get(MESSAGE_TYPE_HEADER)} 입니다.")
            }
        } catch (e: Exception) {
            //TODO: 예외처리
        }
    }
}

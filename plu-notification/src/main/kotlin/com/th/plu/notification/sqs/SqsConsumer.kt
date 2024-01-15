package com.th.plu.notification.sqs

import com.fasterxml.jackson.databind.ObjectMapper
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.InternalServerException
import com.th.plu.external.sqs.dto.FirebaseMessageDto
import com.th.plu.external.sqs.dto.MessageType
import com.th.plu.notification.firebase.FirebaseCloudMessageService
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
    private val LOG_PREFIX = "====> [SQS Queue Request]";

    private val MESSAGE_TYPE_HEADER = "type"

    @SqsListener("pluNotification.fifo")
    fun consume(@Payload info: String, @Headers headers: Map<String, String>) {
        try {
            log.info("${LOG_PREFIX}\ninfo: ${info}\nheaders: ${headers}")
            val messageType = headers.get(MESSAGE_TYPE_HEADER)
            when (messageType) {
                MessageType.FIREBASE.name -> {
                    val firebaseMessageDto = objectMapper.readValue(info, FirebaseMessageDto::class.java)
                    FirebaseCloudMessageService.sendMessageTo(firebaseMessageDto.fcmToken, firebaseMessageDto.title, firebaseMessageDto.body)
                }
                else ->
                    throw InternalServerException(ErrorCode.INTERNAL_SERVER_EXCEPTION, "${LOG_PREFIX} 존재하지 않는 MessageType(${headers.get(MESSAGE_TYPE_HEADER)} 입니다.")
            }
        } catch (exception : Exception) {
            throw Exception(exception.message, exception)
        }
    }
}

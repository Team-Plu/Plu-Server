package com.th.plu.notification.sqs

import com.fasterxml.jackson.databind.ObjectMapper
import com.th.plu.external.firebase.FirebaseCloudMessageService
import com.th.plu.external.sqs.dto.FirebaseMessageDto
import com.th.plu.external.sqs.dto.MessageType
import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.listener.Acknowledgment
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class SqsConsumer(private val objectMapper: ObjectMapper, private val firebaseCloudMessageService: FirebaseCloudMessageService) {

    private val SQS_CONSUME_LOG_MESSAE = "====> [SQS Queue Response]\n" + "info: %s\n" + "header: %s\n";
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val MESSAGE_TYPE_HEADER = "type"

    @SqsListener(value = ["\${cloud.aws.sqs.notification.name}"], deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    fun consume(@Payload info: String, @Headers headers: Map<String, String>, acknowledgment: Acknowledgment) {
        try {
            log.info(String.format(SQS_CONSUME_LOG_MESSAE, info, headers))
            val messageType = headers.get(MESSAGE_TYPE_HEADER)
            when(messageType) {
                MessageType.FIREBASE.name -> {
                    val firebaseMessageDto = objectMapper.readValue(info, FirebaseMessageDto::class.java)
                    firebaseCloudMessageService.sendMessage(firebaseMessageDto)
                }
                else ->
                    throw IllegalArgumentException("존재하지 않는 MessageType(${headers.get(MESSAGE_TYPE_HEADER)} 입니다.")
            }
        } catch (e: Exception) {
            //TODO: 예외처리
        }
    }
}

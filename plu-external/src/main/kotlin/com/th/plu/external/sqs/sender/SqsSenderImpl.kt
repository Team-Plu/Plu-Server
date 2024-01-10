package com.th.plu.external.sqs.sender

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.services.sqs.model.SendMessageResult
import com.fasterxml.jackson.databind.ObjectMapper
import com.th.plu.external.sqs.dto.SqsMessageDto
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class SqsSenderImpl(private val objectMapper: ObjectMapper, private val amazonSQS: AmazonSQS) : SqsSender {

    @Value("\${cloud.aws.sqs.notification.url}")
    private var url: String? = null

    private val log = LoggerFactory.getLogger(this.javaClass)

    private val GROUP_ID = "plu-sqs"

    override fun sendMessage(message: SqsMessageDto): SendMessageResult {
        log.info("[SQS_SENDER] send Message(type: ${message.type})")
        val sendMessageRequest = SendMessageRequest(url, objectMapper.writeValueAsString(message))
            .withMessageGroupId(GROUP_ID)
            .withMessageDeduplicationId(UUID.randomUUID().toString())

        return amazonSQS.sendMessage(sendMessageRequest)
    }
}

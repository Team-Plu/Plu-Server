package com.th.plu.external.sqs.sender

import com.fasterxml.jackson.databind.ObjectMapper
import com.th.plu.external.sqs.dto.FirebaseMessageDto
import io.awspring.cloud.sqs.operations.SendResult
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class SqsSenderImpl(private val objectMapper: ObjectMapper, private val sqsTemplate: SqsTemplate) : SqsSender {

    @Value("\${spring.cloud.aws.sqs.queue.name}")
    private var queueName: String? = null

    private val log = LoggerFactory.getLogger(this.javaClass)
    private val LOG_PREFIX = "====> [SQS_SENDER]"
    private val GROUP_ID = "plu-sqs"
    private val MESSAGE_TYPE_HEADER = "type"

    override fun sendFirebaseMessage(message: FirebaseMessageDto): SendResult<String> {
        log.info("${LOG_PREFIX} send Message(type: ${message.type})")
        return sqsTemplate.send {
            it.queue(queueName.toString())
            it.header(MESSAGE_TYPE_HEADER, message.type.name)
            it.payload(objectMapper.writeValueAsString(message))
            it.messageGroupId(GROUP_ID)
            it.messageDeduplicationId(UUID.randomUUID().toString())
        }
    }
}

package com.th.plu.external.sqs.sender

import com.amazonaws.services.sqs.model.SendMessageResult
import com.th.plu.external.sqs.dto.SqsMessageDto

interface SqsSender {
    fun sendMessage(message: SqsMessageDto): SendMessageResult
}

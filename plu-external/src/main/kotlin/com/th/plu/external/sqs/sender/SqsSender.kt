package com.th.plu.external.sqs.sender

import com.th.plu.external.sqs.dto.FirebaseMessageDto
import io.awspring.cloud.sqs.operations.SendResult

interface SqsSender {
    fun sendFirebaseMessage(message: FirebaseMessageDto): SendResult<String>
}

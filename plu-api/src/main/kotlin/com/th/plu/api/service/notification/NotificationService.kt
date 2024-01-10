package com.th.plu.api.service.notification

import com.th.plu.api.controller.notification.dto.request.MessageSendRequest
import com.th.plu.domain.domain.member.repository.MemberRepository
import com.th.plu.external.sqs.dto.FirebaseMessageDto
import com.th.plu.external.sqs.dto.MessageType
import com.th.plu.external.sqs.sender.SqsSender
import org.springframework.stereotype.Service

@Service
class NotificationService(private val sqsSender: SqsSender, private val memberRepository: MemberRepository) {

    fun sendMessageToAllMember(messageRequest: MessageSendRequest) {
        for (member in memberRepository.findAll()) {
            val sqsMessageDto = FirebaseMessageDto(MessageType.FIREBASE, member.fcmToken, messageRequest.title, messageRequest.body)
            sqsSender.sendMessage(sqsMessageDto)
        }
    }
}

package com.th.plu.api.controller.notification

import com.th.plu.api.controller.notification.dto.request.MessageSendRequest
import com.th.plu.api.dto.ApiResponse
import com.th.plu.api.service.notification.NotificationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class NotificationController(private val notificationService: NotificationService) {

    @PostMapping("/notification/send")
    fun sendMessageToAllMember(@RequestBody messageRequest: MessageSendRequest): ApiResponse<Any> {
        notificationService.sendMessageToAllMember(messageRequest)
        return ApiResponse.success()
    }
}

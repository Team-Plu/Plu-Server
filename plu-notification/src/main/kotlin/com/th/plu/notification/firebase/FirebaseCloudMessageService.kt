package com.th.plu.notification.firebase

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.BadGatewayException
import com.th.plu.external.client.firebase.FirebaseApiCaller
import com.th.plu.notification.firebase.dto.FcmMessageRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class FirebaseCloudMessageService(
    private val firebaseApiCaller: FirebaseApiCaller,
    private val objectMapper: ObjectMapper,
) {
    @Value("\${spring.cloud.firebase.config.path}")
    private var firebaseConfigPath: String? = null

    @Value("\${spring.cloud.firebase.auth.uri}")
    private var firebaseAuthUri: String? = null

    private val log = LoggerFactory.getLogger(this.javaClass)
    private val LOG_PREFIX = "====> [Firebase Cloud Message]"

    fun sendMessageTo(fcmToken: String?, title: String, body: String) {
        if (fcmToken != null) {
            val message = makeMessage(fcmToken, title, body)
            firebaseApiCaller.requestFcmMessaging(getAccessToken(), message)
        }
    }

    fun makeMessage(fcmToken: String, title: String, body: String): String {
        val fcmMessage = FcmMessageRequest.of(fcmToken, title, body)
        return objectMapper.writeValueAsString(fcmMessage)
    }

    fun getAccessToken(): String {
        try {
            val googleCredentials = GoogleCredentials
                .fromStream(ClassPathResource(firebaseConfigPath.toString()).inputStream)
                .createScoped(firebaseAuthUri)
            googleCredentials.refreshIfExpired()

            return googleCredentials.accessToken.tokenValue
        } catch (exception: Exception) {
            log.error(exception.message, exception)
            throw BadGatewayException(
                ErrorCode.BAD_GATEWAY_EXCEPTION,
                "${LOG_PREFIX} FCM Access Token 발급 과정에서 에러가 발생하였습니다."
            )
        }
    }
}

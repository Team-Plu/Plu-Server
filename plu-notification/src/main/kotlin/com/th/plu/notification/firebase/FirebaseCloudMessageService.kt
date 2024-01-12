package com.th.plu.notification.firebase

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.messaging.FirebaseMessaging
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

    fun sendMessageTo(fcmToken: String, title: String, body: String) {
        val message = makeMessage(fcmToken, title, body)
        firebaseApiCaller.requestFcmMessaging(getAccessToken(), message)
    }

    fun makeMessage(fcmToken: String, title: String, body: String): String {
        val fcmMessage = FcmMessageRequest.of(fcmToken, title, body)
        return objectMapper.writeValueAsString(fcmMessage)
    }

    fun getAccessToken(): String {
        val googleCredentials = GoogleCredentials
            .fromStream(ClassPathResource(firebaseConfigPath.toString()).inputStream)
            .createScoped(firebaseAuthUri)
        googleCredentials.refreshIfExpired()

        return googleCredentials.accessToken.tokenValue
    }
}

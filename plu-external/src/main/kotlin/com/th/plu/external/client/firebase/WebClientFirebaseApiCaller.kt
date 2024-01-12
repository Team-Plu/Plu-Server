package com.th.plu.external.client.firebase

import com.th.plu.common.exception.ValidationException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class WebClientFirebaseApiCaller(private val webClient: WebClient) : FirebaseApiCaller {

    @Value("\${spring.cloud.firebase.message.uri}")
    private var messageApiUri: String? = null

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun requestFcmMessaging(accessToken: String, message: String) {
        webClient.post()
            .uri(messageApiUri.toString())
            .headers {
                it.contentType = MediaType.APPLICATION_JSON
                it.setBearerAuth(accessToken)
            }
            .body(BodyInserters.fromValue<String>(message))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) {
                log.error(it.logPrefix())
                Mono.error(ValidationException())
            }
            .onStatus(HttpStatusCode::is5xxServerError) {
                Mono.error(ValidationException())
            }
            .toBodilessEntity()
            .subscribe()

        log.info("전송완")
    }
}

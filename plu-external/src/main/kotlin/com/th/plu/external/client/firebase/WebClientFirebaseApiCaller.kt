package com.th.plu.external.client.firebase

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.BadGatewayException
import com.th.plu.common.exception.model.ValidationException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class WebClientFirebaseApiCaller(private val webClient: WebClient) : FirebaseApiCaller {

    @Value("\${spring.cloud.firebase.message.uri}")
    private var messageApiUri: String? = null

    private val log = LoggerFactory.getLogger(this.javaClass)
    private val LOG_PREFIX = "====> [Firebase Messaging]"


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
                Mono.error(BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION,
                    createFirebaseFailMessage(it, message)
                ))
            }
            .onStatus(HttpStatusCode::is5xxServerError) {
                Mono.error(BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION,
                    createFirebaseFailMessage(it, message)
                ))
            }
            .toBodilessEntity()
            .subscribe {
                if (it.statusCode.is2xxSuccessful) {
                    log.info("${LOG_PREFIX}\nStatus: ${it.statusCode}\nMessage: ${message}")
                } else {
                    throw BadGatewayException(
                        ErrorCode.BAD_GATEWAY_EXCEPTION,
                        "${LOG_PREFIX}\nStatus: ${it.statusCode}\nMessage: ${message}"
                    )
                }
            }
    }

    private fun createFirebaseFailMessage(
        it: ClientResponse,
        message: String
    ) = "${LOG_PREFIX}\nStatus: ${it.statusCode()}\nMessage: ${message}"
}

package com.th.plu.external.config.webclient

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .exchangeStrategies(ExchangeStrategies.withDefaults())
            .clientConnector(ReactorClientHttpConnector(
                HttpClient.from(
                    TcpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                        .doOnConnected {
                            it.addHandlerFirst(ReadTimeoutHandler(3000, TimeUnit.MILLISECONDS))
                        }
                )
            ))
            .build()
    }
}

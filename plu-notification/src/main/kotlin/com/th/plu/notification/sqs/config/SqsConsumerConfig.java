package com.th.plu.notification.sqs.config;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SqsConsumerConfig {
    private final SqsAsyncClient sqsAsyncClient;

    @Bean
    public SqsMessageListenerContainerFactory<Object> sqsMessageListenerContainerFactory() {
        return SqsMessageListenerContainerFactory.builder().
                sqsAsyncClient(sqsAsyncClient)
                .build();
    }
}

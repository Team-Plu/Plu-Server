package com.th.plu.external.sqs.config

import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Configuration
class SqsProducerConfig {

    @Value("\${spring.cloud.aws.credentials.access-key}")
    private var accessKey: String? = null

    @Value("\${spring.cloud.aws.credentials.secret-key}")
    private var secretKey: String? = null

    @Value("\${spring.cloud.aws.region.static}")
    private var region: String? = null


    // Client 설정
    @Primary
    @Bean
    fun sqsAsyncClient(): SqsAsyncClient {
        return SqsAsyncClient.builder()
            .credentialsProvider(({
                object : AwsCredentials {
                    override fun accessKeyId(): String {
                        return accessKey.toString()
                    }

                    override fun secretAccessKey(): String {
                        return secretKey.toString()
                    }
                }
            }))
            .region(Region.of(region))
            .build()
    }

    // 메시지 발송을 위한 SQS 템플릿 설정
    @Bean
    fun sqsTemplate(): SqsTemplate {
        return SqsTemplate.newTemplate(sqsAsyncClient())
    }
}

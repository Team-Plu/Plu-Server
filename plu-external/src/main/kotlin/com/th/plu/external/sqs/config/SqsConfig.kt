package com.th.plu.external.sqs.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class SqsConfig {

    @Value("\${cloud.aws.credentials.accessKey}")
    private var accessKey: String? = null

    @Value("\${cloud.aws.credentials.secretKey}")
    private var secretKey: String? = null

    @Value("\${cloud.aws.region.static}")
    private var region: String? = null


    @Primary
    @Bean
    fun amazonSQSAws(): AmazonSQSAsync {
        val awsCredentials = BasicAWSCredentials(accessKey, secretKey)
        return AmazonSQSAsyncClientBuilder.standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .build()
    }
}

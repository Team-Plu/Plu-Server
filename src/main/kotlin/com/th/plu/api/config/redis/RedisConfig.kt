package com.th.plu.api.config.redis

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.session.data.redis.config.ConfigureRedisAction

@Configuration
@EnableRedisRepositories
class RedisConfig(private val redisProperties: RedisProperties) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        with(redisTemplate) {
            connectionFactory = redisConnectionFactory()
            keySerializer = StringRedisSerializer()
            valueSerializer = StringRedisSerializer()
        }

        return redisTemplate
    }

    @Bean
    fun configureRedisAction(): ConfigureRedisAction {
        return ConfigureRedisAction.NO_OP
    }
}

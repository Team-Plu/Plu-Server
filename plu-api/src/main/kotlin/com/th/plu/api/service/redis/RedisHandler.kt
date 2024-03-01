package com.th.plu.api.service.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisHandler(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun get(key: String): String? {
        return redisTemplate.opsForValue().get(key) as String?
    }

    fun set(key: String, value: Any, timeout: Long) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS)
    }

    fun delete(key: String) {
        redisTemplate.delete(key)
    }
}
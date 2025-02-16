package io.github.mgrzeszczak.offer.service.api

import io.netty.util.internal.ThreadLocalRandom
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/api/offers")
class OfferController(
    private val redisTemplate: RedisTemplate<String, String>
) {

    @GetMapping
    fun getOffers() {
        redisTemplate.opsForValue().set("test", "123", Duration.ofMinutes(5))
        Thread.sleep(Duration.ofMillis(ThreadLocalRandom.current().nextLong(30, 50)))
    }

}
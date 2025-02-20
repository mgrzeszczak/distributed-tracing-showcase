package io.github.mgrzeszczak.offer.service.api

import io.micrometer.tracing.annotation.NewSpan
import io.netty.util.internal.ThreadLocalRandom
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/api/offers")
class OfferController(
    private val redisTemplate: RedisTemplate<String, String>,
    private val jdbcTemplate: JdbcTemplate,
    private val internalOfferService: InternalOfferService
) {

    @GetMapping
    fun getOffers() {
        redisTemplate.opsForValue().set("test", "123", Duration.ofMinutes(5))
        jdbcTemplate.queryForObject("select version()", String::class.java)
        internalOfferService.internalGetOffers()
        Thread.sleep(Duration.ofMillis(ThreadLocalRandom.current().nextLong(30, 50)))
    }


}

@Component
class InternalOfferService {

    @NewSpan("internalGetOffers")
    fun internalGetOffers() {
        Thread.sleep(Duration.ofMillis(ThreadLocalRandom.current().nextLong(30, 50)))
    }

}
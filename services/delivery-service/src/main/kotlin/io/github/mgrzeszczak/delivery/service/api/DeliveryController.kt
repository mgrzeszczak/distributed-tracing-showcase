package io.github.mgrzeszczak.delivery.service.api

import io.netty.util.internal.ThreadLocalRandom
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/api/delivery")
class DeliveryController {

    @GetMapping
    fun getDelivery() {
        Thread.sleep(Duration.ofMillis(ThreadLocalRandom.current().nextLong(50, 100)))
    }

}
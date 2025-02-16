package io.github.mgrzeszczak.cart.service.api

import io.github.mgrzeszczak.cart.service.client.DeliveryServiceClient
import io.github.mgrzeszczak.cart.service.client.OfferServiceClient
import io.netty.util.internal.ThreadLocalRandom
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.time.Duration

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val deliveryServiceClient: DeliveryServiceClient,
    private val offerServiceClient: OfferServiceClient
) {

    @GetMapping
    fun calculateCart() {
        try {
            deliveryServiceClient.getDelivery()
            offerServiceClient.getOffers()
            Thread.sleep(Duration.ofMillis(ThreadLocalRandom.current().nextLong(100, 300)))
        } catch (e: Exception) {
            e.toString()
        }
    }

}
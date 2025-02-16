package io.github.mgrzeszczak.cart.service.client

import org.springframework.web.service.annotation.GetExchange


interface DeliveryServiceClient {

    @GetExchange("/api/delivery")
    fun getDelivery()

}
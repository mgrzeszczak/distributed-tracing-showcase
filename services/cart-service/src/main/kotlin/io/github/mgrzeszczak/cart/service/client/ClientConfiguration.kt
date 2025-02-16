package io.github.mgrzeszczak.cart.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

@Configuration
class ClientConfiguration {

    @Bean
    fun offerServiceClient(
        restClientBuilder: RestClient.Builder,
        @Value("\${client.offer-service.url}") offerServiceBaseUrl: String
    ): OfferServiceClient {
        val restClient = restClientBuilder
            .baseUrl(offerServiceBaseUrl)
            .build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()
        return factory.createClient()
    }

    @Bean
    fun deliveryServiceClient(
        restClientBuilder: RestClient.Builder,
        @Value("\${client.delivery-service.url}") deliveryServiceBaseUrl: String
    ): DeliveryServiceClient {
        val restClient = restClientBuilder
            .baseUrl(deliveryServiceBaseUrl)
            .build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()
        return factory.createClient()
    }

}
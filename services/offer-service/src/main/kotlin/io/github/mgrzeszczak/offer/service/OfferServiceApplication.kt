package io.github.mgrzeszczak.offer.service

import io.lettuce.core.resource.ClientResources
import io.lettuce.core.tracing.MicrometerTracing
import io.micrometer.observation.ObservationRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@EnableCaching
class OfferServiceApplication

fun main(args: Array<String>) {
    runApplication<OfferServiceApplication>(*args)
}

@Configuration
class ObservabilityConfiguration {

    @Bean
    fun clientResources(observationRegistry: ObservationRegistry): ClientResources {
        return ClientResources.builder()
            .tracing(
                MicrometerTracing(observationRegistry, "redis", true)
            )
            .build()
    }

}
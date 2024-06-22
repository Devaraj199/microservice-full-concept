package com.eazybytes.gatewayserver;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
			return routeLocatorBuilder.routes()
					.route(p->p
							.path("/eazybank/accounts/**")
							.filters(f->f.rewritePath("/eazybank/accounts/(?<segment>.*)","/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime	.now().toString())
									.retry(retryConfig -> retryConfig.setRetries(5))
									.circuitBreaker(config -> config.setName("accountCircuitBreaker")
											.setFallbackUri("forward:/contactSupport")))
							.uri("lb://ACCOUNTS"))
					.route(p->p
							.path("/eazybank/loans/**")
							.filters(f->f.rewritePath("/eazybank/loans/(?<segment>.*)","/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
							.uri("lb://LOANS"))
					.route(p->p
							.path("/eazybank/cards/**")
							.filters(f->f.rewritePath("/cards/accounts/(?<segment>.*)","/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
									.retry(retryConfig -> retryConfig.setRetries(3)
											.setBackoff(Duration.ofMillis(1000),Duration.ofMillis(1000),2,true)))
							.uri("lb://CARDS")).build();
	}
}

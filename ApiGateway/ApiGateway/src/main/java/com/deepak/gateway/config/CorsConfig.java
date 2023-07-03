package com.deepak.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CorsConfig implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		if (CorsUtils.isCorsRequest(exchange.getRequest())) {
			CorsConfiguration corsConfig = new CorsConfiguration();
			corsConfig.addAllowedOrigin("*");
			corsConfig.addAllowedHeader("*");
			corsConfig.addAllowedMethod(HttpMethod.GET);
			corsConfig.addAllowedMethod(HttpMethod.POST);
			corsConfig.addAllowedMethod(HttpMethod.PUT);
			corsConfig.addAllowedMethod(HttpMethod.DELETE);

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", corsConfig);

			exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
			exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
			exchange.getResponse().getHeaders().add("Access-Control-Max-Age", "1800");
			exchange.getResponse().getHeaders().add("Access-Control-Expose-Headers", "*");

			if (CorsUtils.isPreFlightRequest(exchange.getRequest())) {
				exchange.getResponse().setStatusCode(HttpStatus.OK);
				return Mono.empty();
			}
		}

		return chain.filter(exchange);
	}
}

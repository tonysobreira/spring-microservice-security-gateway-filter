package com.javatechie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import com.javatechie.filter.SwaggerFilter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "API Gateway", version = "1.0", description = "Documentation API Gateway v1.0"))
public class SwiggyGatewayApplication {
	
	@Autowired
	private SwaggerFilter swaggerFilter;

	public static void main(String[] args) {
		SpringApplication.run(SwiggyGatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder
				.routes()
				.route(r -> 
					r.path("/restaurant-service/v3/api-docs")
					.and()
					.method(HttpMethod.GET)
					.filters(f -> f.filters(swaggerFilter))
					.uri("lb://restaurant-service"))
				.route(r -> r.path("/swiggy-app/v3/api-docs").and().method(HttpMethod.GET).uri("lb://swiggy-app"))
//				.route(r -> r.path("/price-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://price-service"))
//				.route(r -> r.path("/api/v1/products/{id}").and().method(HttpMethod.GET).uri("lb://product-service"))
//				.route(r -> r.path("/api/v1/products/{id}").and().method(HttpMethod.PUT).uri("lb://product-service"))
//				.route(r -> r.path("/api/v1/products/{id}").and().method(HttpMethod.DELETE).uri("lb://product-service"))
//				.route(r -> r.path("/api/v1/products").and().method(HttpMethod.GET).uri("lb://product-service"))
//				.route(r -> r.path("/api/v1/products").and().method(HttpMethod.POST).uri("lb://product-service"))
//				.route(r -> r.path("/api/v1/products").and().method(HttpMethod.PUT).uri("lb://product-service"))
//				.route(r -> r.path("/api/v1/products").and().method(HttpMethod.DELETE).uri("lb://product-service"))
//				.route(r -> r.path("/api/v1/price").and().method(HttpMethod.GET).uri("lb://price-service"))
				.build();
	}

}

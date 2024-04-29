package com.javatechie.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.javatechie.util.JwtUtil;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private RestTemplate template;

	@Autowired
	private JwtUtil jwtUtil;

	private WebClient webClient;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Autowired
	public AuthenticationFilter(WebClient.Builder webClientBuilder) {
		super(Config.class);
		this.webClient = webClientBuilder.build();
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			final ServerHttpRequest request;

			if (validator.isSecured.test(exchange.getRequest())) {
				// header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}

				final String token = authHeader;

				String expectedRole = config.role;
				System.out.println(expectedRole);

				request = exchange
						.getRequest()
						.mutate()
						.header("loggedInUser", jwtUtil.extractUsername(token))
						.build();

				return this.webClient.get()
						.uri("http://IDENTITY-SERVICE/auth/validate?token=" + token + "&role=" + expectedRole)
						.retrieve()
						.bodyToMono(String.class)
						.flatMap(response -> {

							System.out.println("response...");
							System.out.println(response);

							if ("Token is valid".equals(response)) {
								return chain.filter(exchange.mutate().request(request).build());
							}

							throw new RuntimeException("un authorized access to application");
						}).onErrorResume(throwable -> {
							throw new RuntimeException("un authorized access to application");
						});

			}
			return chain.filter(exchange);
		});
	}

	public static class Config {

		private String role;

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

	}

}

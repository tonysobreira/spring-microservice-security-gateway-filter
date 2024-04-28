package com.javatechie.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

//	@Autowired
//	private RestTemplate template;

//	@Autowired
//	private JwtUtil jwtUtil;
	
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
			if (validator.isSecured.test(exchange.getRequest())) {
				// header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}

//				try {
//					REST call to AUTH service
//					String response = template.getForObject("http://IDENTITY-SERVICE/auth/validate?token=" + authHeader, String.class);
//					System.out.println(response);
					
//					.uri("http://authentication-service/auth/validate?token=" + authHeader + "&role=" + expectedRole)
					
					String expectedRole = config.role;
					System.out.println(expectedRole);
					 
					return this.webClient
	                    .get()
	                    .uri("http://IDENTITY-SERVICE/auth/validate?token=" + authHeader + "&role=" + expectedRole)
	                    .retrieve()
	                    .bodyToMono(String.class)
	                    .flatMap(response -> {
	                    	
	                    	System.out.println("response...");
	                    	System.out.println(response);
	                    	
	                    	if ("Token is valid".equals(response)) {
	                    		return chain.filter(exchange);
	                    	}
	                    	
	                    	throw new RuntimeException("un authorized access to application");
	                    	
//	                        if(Boolean.FALSE.equals(response)) {
//	                            throw new RuntimeException("un authorized access to application");
//	                        }
//	                        
//	                        return chain.filter(exchange);
	                    })
	                    .onErrorResume(throwable -> {
	                        throw new RuntimeException("un authorized access to application");
	                    });
					
					
//					jwtUtil.validateToken(authHeader);
//				} catch (Exception e) {
//					System.out.println("invalid access...!");
//					throw new RuntimeException("un authorized access to application");
//				}
//
//			}
//			return chain.filter(exchange);
//		});
//	}
			}
			return null;
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

package com.project.demomicroserviceopenfeign.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demomicroserviceopenfeign.common.external.ExternalServiceClient;
import com.project.demomicroserviceopenfeign.common.internal.InternalServiceClient;

@RestController
@RequestMapping("/demo-microservice-open-feign")
public class OpenFeignController {

	private final InternalServiceClient internalServiceClient;

	private final ExternalServiceClient externalServiceClient;

	public OpenFeignController(InternalServiceClient internalServiceClient,
			ExternalServiceClient externalServiceClient) {
		this.internalServiceClient = internalServiceClient;
		this.externalServiceClient = externalServiceClient;
	}

	@GetMapping("/internal")
	public String internalCall() {
		return "this is internal OpenFeign call: " + internalServiceClient.getInternalResource();
	}

	@GetMapping("/external")
	public ResponseEntity<String> externalCall() {
		return externalServiceClient.getExternalResource("API_KEY", "SEARCH_ENGINE", "SEARCH_QUERY");
	}

}

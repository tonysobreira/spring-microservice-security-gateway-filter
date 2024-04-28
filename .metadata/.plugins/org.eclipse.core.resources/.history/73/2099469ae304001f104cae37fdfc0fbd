package com.project.demomicroserviceopenfeign.common.internal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "InternalServiceClient", url = "http://localhost:7080/demo-microservice")
public interface InternalServiceClient {

	@GetMapping("/test")
	String getInternalResource();

}
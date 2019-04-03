package com.ps.sp.zipkinservice;

import brave.sampler.Sampler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class ZipkinServiceApplication {


	@LoadBalanced
	private RestTemplate restTemplate = new RestTemplate();

	private static final Log log = LogFactory.getLog(ZipkinServiceApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(ZipkinServiceApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		log.info("hello from trace");

		Object object = this.restTemplate.exchange("http://user-service/users/prasad",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Object>() {
				});

		return "Hello World" ;
	}

	@Bean
	Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}

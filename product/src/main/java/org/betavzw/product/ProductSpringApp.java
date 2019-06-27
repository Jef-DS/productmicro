package org.betavzw.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching //local cache (in the same JVM) -> see ProductEntityRepository
public class ProductSpringApp {

	public static void main(String[] args) {
		SpringApplication.run(ProductSpringApp.class, args);

	}
	
	//Autowiring RestTemplate for ProductClient
	@Bean
	@LoadBalanced //use Ribbon load balancer
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

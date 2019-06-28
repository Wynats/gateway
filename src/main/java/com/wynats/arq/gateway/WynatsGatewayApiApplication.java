package com.wynats.arq.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.wynats.arq.gateway.filter.JwtFilter;
import com.wynats.arq.gateway.filter.LoggerFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class WynatsGatewayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WynatsGatewayApiApplication.class, args);
	}
	@Bean
	public LoggerFilter preFilter() {
        return new LoggerFilter();
    }
	
	@Bean
	public JwtFilter preFilterJwt() {
        return new JwtFilter();
    }
    
}

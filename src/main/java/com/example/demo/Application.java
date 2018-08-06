package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//启用服务注册功能，将服务注册到注册中心
@EnableDiscoveryClient
//启用消费客户端功能
@EnableFeignClients
//启用自动从配置中心获取配置
@SpringBootApplication
@ComponentScan("controller")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

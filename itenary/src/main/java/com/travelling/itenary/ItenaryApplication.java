package com.travelling.itenary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.travelling") 
public class ItenaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItenaryApplication.class, args);
	}

}

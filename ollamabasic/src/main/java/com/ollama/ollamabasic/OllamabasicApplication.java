package com.ollama.ollamabasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ollama") 
public class OllamabasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(OllamabasicApplication.class, args);
		System.out.println("Testing if dummy project works");
		System.out.println(5+2);
	}

}

package com.codeanalyzer.code_analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.codeanalyzer")
public class CodeAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeAnalyzerApplication.class, args);
	}

}

package com.ocr.ocr_space;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ocr")
public class OcrSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcrSpaceApplication.class, args);
	}

}

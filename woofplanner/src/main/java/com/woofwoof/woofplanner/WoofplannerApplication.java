package com.woofwoof.woofplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WoofplannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoofplannerApplication.class, args);
	}

}

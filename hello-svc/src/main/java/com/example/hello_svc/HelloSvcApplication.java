package com.example.hello_svc;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HelloSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSvcApplication.class, args);
	}

	// api to get time
	@GetMapping("/time")
	public String getTime() {
		return "Current time is: " + LocalDateTime.now();
	}

}

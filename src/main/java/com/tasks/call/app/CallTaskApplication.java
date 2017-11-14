package com.tasks.call.app;

import com.tasks.call.app.repository.CallRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"model", "repository"})
public class CallTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CallTaskApplication.class, args);
	}

    @Bean
    public CommandLineRunner demo(CallRepository repository) {
        return (args) -> {
            System.out.println("hello world");
        };
    }
}

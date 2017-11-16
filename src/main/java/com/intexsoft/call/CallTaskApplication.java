package com.intexsoft.call;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * <p>CallTaskApplication represents SpringBootApplication</p>
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.intexsoft.call.model", "com.intexsoft.call.repository", "com.intexsoft.call.service"})
public class CallTaskApplication {

    /**
     * <p>Entry point of the application</p>
     *
     * @param args - command-line arguments of the application
     */
    public static void main(String[] args) {
        SpringApplication.run(CallTaskApplication.class, args);
    }
}

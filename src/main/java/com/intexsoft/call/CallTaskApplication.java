package com.intexsoft.call;

import com.intexsoft.call.repository.CallRepository;
import com.intexsoft.call.service.CallWatcher;
import com.intexsoft.call.service.CallWatcherRunner;
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

    /**
     * <p>Creates and executes CallWatcherRunner for looking for incoming call-files</p>
     *
     * @param callRepository CRUD Repository for Call entity
     * @return CallWatcherRunner - CommandLineRunner interface realisation
     */
    @Bean
    public CallWatcherRunner runCallWatcher(final CallRepository callRepository) {
        return new CallWatcherRunner(callRepository);
    }
}

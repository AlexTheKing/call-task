package com.intexsoft.call;

import com.intexsoft.call.service.CallWatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>CallTaskApplication represents SpringApplication</p>
 */
public class CallTaskApplication {

    private static final String SPRING_CONFIG = "spring-config.xml";
    private static final String SERVICE_NAME = "callWatcherService";
    private static final Logger LOGGER = LoggerFactory.getLogger(CallTaskApplication.class);

    /**
     * <p>Entry point of the application</p>
     *
     * @param args - command-line arguments of the application
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(SPRING_CONFIG);
        CallWatcherService service = ((CallWatcherService) context.getBean(SERVICE_NAME));
        LOGGER.info("Application initialized");
        service.run();
        LOGGER.info("Exiting the application..");
    }
}

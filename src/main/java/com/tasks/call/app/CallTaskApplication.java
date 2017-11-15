package com.tasks.call.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasks.call.app.model.Call;
import com.tasks.call.app.repository.ICallRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

@SpringBootApplication
@ComponentScan(basePackages = {"model", "repository"})
public class CallTaskApplication {

    private static final String CURRENT_DIRECTORY = "";

    public static void main(String[] args) {
        SpringApplication.run(CallTaskApplication.class, args);
    }

    @Bean
    public CommandLineRunner process(final ICallRepository callRepository) {
        return (args) -> {
            final Path currentDir = Paths.get(CURRENT_DIRECTORY);
            System.out.println("Working directory: " + currentDir.toAbsolutePath().toString());
            ObjectMapper mapper = new ObjectMapper();

            try {
                final WatchService watchService = FileSystems.getDefault().newWatchService();
                currentDir.register(watchService, ENTRY_CREATE);

                while (true) {
                    try {
                        final WatchKey key = watchService.take();

                        for (WatchEvent<?> event : key.pollEvents()) {
                            final WatchEvent.Kind<?> kind = event.kind();

                            if (kind == OVERFLOW) {
                                continue;
                            }

                            final WatchEvent<Path> ev = (WatchEvent<Path>) event;
                            final Path filename = ev.context();
                            final Path child = currentDir.resolve(filename);
                            final Call call = mapper.readValue(child.toFile(), Call.class);

                            if (call != null) {
                                callRepository.save(call);
                            }
                        }

                        boolean valid = key.reset();

                        if (!valid) {
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}

package com.tasks.call.app;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

@SpringBootApplication
@ComponentScan(basePackages = {"model", "repository"})
public class CallTaskApplication {

    private static final String CURRENT_DIRECTORY = "";

    public static void main(String[] args) {
        SpringApplication.run(CallTaskApplication.class, args);
    }

    /**
     * <p>Creates and uses File Watcher for looking for incoming files</p>
     * <p>Watcher is created in directory, where application is launched</p>
     *
     * @param callRepository CRUD Repository for Call Entity
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner processFileWatcher(final ICallRepository callRepository) {
        return (args) -> {
            final Path currentDir = Paths.get(CURRENT_DIRECTORY);
            System.out.println("Working directory: " + currentDir.toAbsolutePath().toString());
            ObjectMapper mapper = new ObjectMapper();

            try {
                final WatchService watchService = FileSystems.getDefault().newWatchService();
                currentDir.register(watchService, ENTRY_CREATE);
                WatchKey key = null;

                do {
                    try {
                        key = watchService.take();
                        List<WatchEvent<?>> events = key.pollEvents();
                        events.removeIf(event -> event.kind() == OVERFLOW);
                        List<Path> paths = ((List<Path>) events.stream().map(WatchEvent::context).collect(Collectors.toList()));

                        for (Path path : paths) {
                            final Path child = currentDir.resolve(path);

                            try {
                                final Call call = mapper.readValue(child.toFile(), Call.class);
                                callRepository.save(call);
                            } catch (JsonMappingException | JsonParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (key.reset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}

package com.intexsoft.call.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intexsoft.call.model.Call;
import com.intexsoft.call.repository.CallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class CallWatcher {

    private Logger logger = LoggerFactory.getLogger(CallWatcher.class);
    private final Path directory;
    private final CallRepository callRepository;
    private final ObjectMapper objectMapper;

    /**
     * <p>Creates CallWatcher for specified directory</p>
     *
     * @param directoryName - directory with incoming files
     * @param repository - CRUD Repository for Call entity
     */
    public CallWatcher(final String directoryName, final CallRepository repository) {
        this.directory = Paths.get(directoryName);
        this.callRepository = repository;
        objectMapper = new ObjectMapper();
        logger.info("Working directory: " + directory.toAbsolutePath().toString());
    }

    /**
     * <p>Starts watching a directory for incoming files</p>
     */
    public void watch() {
        WatchService watchService = registerWatchService(directory);
        boolean validResult = true;

        while (validResult) {
            validResult = takeKey(watchService);
        }
    }

    private WatchService registerWatchService(final Path directory) {
        WatchService watchService = null;

        try {
            watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService, ENTRY_CREATE);
        } catch (IOException e) {
            logger.error(e.toString(), e);
        }

        return watchService;
    }

    private boolean takeKey(final WatchService watchService) {
        boolean valid = true;

        try {
            final WatchKey key = watchService.take();
            List<Path> paths = getPaths(key);
            processPaths(paths);
            valid = key.reset();
        } catch (InterruptedException e) {
            logger.error(e.toString(), e);
        }

        return valid;
    }

    private List<Path> getPaths(final WatchKey key) {
        List<WatchEvent<?>> events = key.pollEvents();
        events.removeIf(event -> event.kind() == OVERFLOW);

        return ((List<Path>) events.stream().map(WatchEvent::context).collect(Collectors.toList()));
    }

    private void processPaths(final List<Path> paths) {
        for (Path path : paths) {
            final Path child = directory.resolve(path);
            readAndSave(child);
        }
    }

    private void readAndSave(final Path path) {
        try {
            final Call call = objectMapper.readValue(path.toFile(), Call.class);
            callRepository.save(call);
        } catch (IOException e) {
            logger.error(e.toString(), e);
        }
    }
}

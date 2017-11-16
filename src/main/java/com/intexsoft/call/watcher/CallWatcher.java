package com.intexsoft.call.watcher;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

/**
 * <p>CallWatcher class</p>
 * Watches for incoming files of some specified directory,
 * parses them according to the model and stores in the database,
 * using specified repository
 */
public class CallWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallWatcher.class);
    private Path directory;
    private CallRepository callRepository;
    private ObjectMapper objectMapper;
    private WatchService watchService;
    private String directoryPath;

    /**
     * <p>Creates CallWatcher for specified directory</p>
     *
     * @param directoryName - directory with incoming files
     * @param repository    - CRUD Repository for Call entity
     */
    public CallWatcher(String directoryName, CallRepository repository) {
        this.directory = Paths.get(directoryName);
        this.directoryPath = directory.toAbsolutePath().toString();
        this.callRepository = repository;
        this.objectMapper = new ObjectMapper();
        this.watchService = registerWatchService(directory);
        LOGGER.info("Working directory: " + directoryPath);
    }

    /**
     * <p>Starts watching a directory for incoming files</p>
     */
    public void watch() {
        boolean validResult = true;
        while (validResult) {
            validResult = takeKey();
        }
    }

    private WatchService registerWatchService(Path directory) {
        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService, ENTRY_CREATE);
        } catch (IOException e) {
            String message = String.format("Cannot register WatchService for directory %s", directoryPath);
            LOGGER.error(message, e);
        }
        return watchService;
    }

    private boolean takeKey() {
        boolean valid = true;
        try {
            WatchKey key = watchService.take();
            List<Path> paths = getPaths(key);
            processPaths(paths);
            valid = key.reset();
        } catch (InterruptedException e) {
            String message = "WatchService was interrupted during waiting for WatchKey in directory %s";
            String fullMessage = String.format(message, directoryPath);
            LOGGER.error(fullMessage, e);
        }
        return valid;
    }

    private List<Path> getPaths(WatchKey key) {
        List<WatchEvent<?>> events = key.pollEvents();
        events.removeIf(event -> event.kind() == OVERFLOW);
        return ((List<Path>) events.stream().map(WatchEvent::context).collect(Collectors.toList()));
    }

    private void processPaths(List<Path> paths) {
        for (Path path : paths) {
            Path child = directory.resolve(path);
            readAndSave(child);
        }
    }

    private void readAndSave(Path path) {
        try {
            Call call = objectMapper.readValue(path.toFile(), Call.class);
            callRepository.save(call);
        } catch (JsonMappingException | JsonParseException e) {
            String pathToFile = path.toAbsolutePath().toString();
            String message = String.format("Cannot parse or map file %s", pathToFile);
            LOGGER.error(message, e);
        } catch (IOException e) {
            String pathToFile = path.toAbsolutePath().toString();
            String message = String.format("Cannot open file %s", pathToFile);
            LOGGER.error(message, e);
        }
    }
}

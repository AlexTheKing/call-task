package com.intexsoft.call.service;

import com.intexsoft.call.repository.CallRepository;
import com.intexsoft.call.watcher.CallWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>CallWatcherService class</p>
 * Runs CallWatcher
 * Automatically starts on application launch
 */
@Service
public class CallWatcherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallWatcherService.class);
    private static final String CURRENT_DIRECTORY = "";
    private String directory;

    @Autowired
    private CallRepository callRepository;

    /**
     * <p>Creates CallWatcherService with specified repository</p>
     *
     * @param directory - Directory, where CallWatcher needs to be launched
     */
    public CallWatcherService(String directory) {
        this.directory = Optional.ofNullable(directory).orElse(CURRENT_DIRECTORY);
    }

    /**
     * <p>Creates and runs CallWatcher</p>
     */
    public void run() {
        CallWatcher watcher = new CallWatcher(directory, callRepository);
        LOGGER.debug("Starting CallWatcher");
        watcher.watch();
    }
}

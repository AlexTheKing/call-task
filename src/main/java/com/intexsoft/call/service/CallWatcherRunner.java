package com.intexsoft.call.service;

import com.intexsoft.call.repository.CallRepository;
import org.springframework.boot.CommandLineRunner;

public class CallWatcherRunner implements CommandLineRunner {

    private static final String CURRENT_DIRECTORY = "";
    private CallRepository callRepository;

    /**
     * <p>Creates CallWatcherRunner with specified repository</p>
     *
     * @param repository - CRUD Repository for Call entity
     */
    public CallWatcherRunner(CallRepository repository) {
        this.callRepository = repository;
    }

    /**
     * <p>Creates and runs CallWatcher</p>
     *
     * @param args - command-line application arguments
     * @throws Exception - any exception that may be thrown during execution
     */
    @Override
    public void run(String... args) throws Exception {
        final CallWatcher watcher = new CallWatcher(CURRENT_DIRECTORY, callRepository);
        watcher.watch();
    }
}

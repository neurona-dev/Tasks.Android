package dev.neurona.tasks.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorProvider {
    private static ExecutorProvider instance;
    private Executor executor;

    private ExecutorProvider() {
        executor = Executors.newSingleThreadExecutor();
    }

    public static synchronized ExecutorProvider getInstance() {
        if (instance == null) {
            instance = new ExecutorProvider();
        }
        return instance;
    }

    public Executor getExecutor() {
        return executor;
    }
}

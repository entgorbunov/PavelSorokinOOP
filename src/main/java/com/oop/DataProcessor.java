package com.oop;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final AtomicInteger taskCounter = new AtomicInteger(0);
    private final HashMap<String, Integer> taskResults = new HashMap<>();

    public void submitTask(List<Integer> tasks) {
        String taskName = "Task :" + taskCounter.incrementAndGet();
        CalculateSumTask task = new CalculateSumTask(tasks, taskName);

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                logger.info("Task {} submitted for processing", taskName);
                return task.call();
            } catch (Exception e) {
                throw new IllegalStateException("Task failed", e);
            }
        }, executor);

        future.thenAccept(result -> {
            synchronized (taskResults) {
                taskResults.put(taskName, result);
            }
            logger.info("Task {} completed with result {}", taskName, result);
        });
    }

    public int getTaskCount() {
        return taskCounter.get() - taskResults.size();
    }

    public Optional<Integer> getTaskResult(String taskName) {
        synchronized (taskResults) {
            return Optional.ofNullable(taskResults.get(taskName));
        }
    }

    public void shutDown() {
        executor.shutdown();
    }

}

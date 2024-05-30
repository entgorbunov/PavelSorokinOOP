package com.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class CalculateSumTask implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(CalculateSumTask.class);

    private final List<Integer> numbers;
    private final String taskName;

    public CalculateSumTask(List<Integer> numbers, String taskName) {
        this.numbers = numbers;
        this.taskName = taskName;
    }

    @Override
    public Integer call() throws Exception {
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        Thread.sleep(200);
        logger.info("Thread: {}, Task: {}", Thread.currentThread().getName(), taskName);
        return sum;
    }
}

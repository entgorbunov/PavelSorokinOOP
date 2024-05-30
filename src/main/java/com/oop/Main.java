package com.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        DataProcessor dataProcessor = new DataProcessor();

        for (int i = 0; i < 100; i++) {
            List<Integer> integerList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                integerList.add(j);
            }
            dataProcessor.submitTask(integerList);
        }

        while (dataProcessor.getTaskCount() > 0) {
            logger.info("Active tasks: {}", dataProcessor.getTaskCount());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread was interrupted", e);
            }
        }

        for (int i = 0; i < 100; i++) {
            String taskName = "task " + i;
            dataProcessor.getTaskResult(taskName).ifPresent(result -> {
                logger.info("Result of {}: {}", taskName, result);
            });
        }
        dataProcessor.shutDown();
    }

}

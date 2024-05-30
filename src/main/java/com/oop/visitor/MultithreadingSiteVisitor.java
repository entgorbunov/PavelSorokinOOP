package com.oop.visitor;

import com.oop.counter.SiteVisitCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadingSiteVisitor {
    private final SiteVisitCounter siteVisitCounter;
    private long startTime;
    private long endTime;

    public MultithreadingSiteVisitor(SiteVisitCounter siteVisitCounter) {
        this.siteVisitCounter = siteVisitCounter;
    }

    public void visitMultithread(int numOfThreads, String visitorName) {
        List<Thread> threads = new ArrayList<>();
        startTime = System.nanoTime();
        for (int i = 0; i < numOfThreads; i++) {
            Thread thread = new Thread(siteVisitCounter::incrementVisitCount);
            threads.add(thread);
            thread.start();
        }
        waitUntilAllVisited(threads);
        endTime = System.nanoTime();
        System.out.println(visitorName + " Total time of handling: " + getTotalTimeOfHandling() + " seconds");

    }

    public double getTotalTimeOfHandling() {
        return (endTime - startTime) / 1_000_000_000.0;
    }

    private void waitUntilAllVisited(List<Thread> threadList) {
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}

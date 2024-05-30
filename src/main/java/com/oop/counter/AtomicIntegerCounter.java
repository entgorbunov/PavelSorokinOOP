package com.oop.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCounter implements SiteVisitCounter {
    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    public void incrementVisitCount() {
        atomicInteger.incrementAndGet();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int getVisitCount() {
        return atomicInteger.get();
    }
}

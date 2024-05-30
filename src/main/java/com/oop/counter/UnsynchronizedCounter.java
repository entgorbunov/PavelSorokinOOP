package com.oop.counter;

public class UnsynchronizedCounter implements SiteVisitCounter {
    private int counter = 0;
    @Override
    public void incrementVisitCount() {
        counter++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int getVisitCount() {
        return counter;
    }
}

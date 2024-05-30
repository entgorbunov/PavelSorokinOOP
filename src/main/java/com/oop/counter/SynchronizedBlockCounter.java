package com.oop.counter;

public class SynchronizedBlockCounter implements SiteVisitCounter {
    private Integer counter = 0;
    @Override
    public void incrementVisitCount() {
        synchronized (this) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            counter++;
        }
    }

    @Override
    public int getVisitCount() {
        synchronized (this) {
            return counter;
        }
    }
}

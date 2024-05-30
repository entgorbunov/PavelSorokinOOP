package com.oop;

import com.oop.counter.*;
import com.oop.visitor.MultithreadingSiteVisitor;

public class Main {
    public static void main(String[] args) {
        SiteVisitCounter atomicIntegerCounter = new AtomicIntegerCounter();
        SiteVisitCounter reentrantLockCounter = new ReentrantLockCounter();
        SiteVisitCounter synchronizedBlockCounter = new SynchronizedBlockCounter();
        SiteVisitCounter unSynchronizedCounter = new UnsynchronizedCounter();
        SiteVisitCounter volatileCounter = new VolatileCounter();

        MultithreadingSiteVisitor atomicVisitor = new MultithreadingSiteVisitor(atomicIntegerCounter);
        MultithreadingSiteVisitor lockVisitor = new MultithreadingSiteVisitor(reentrantLockCounter);
        MultithreadingSiteVisitor synchVisitor = new MultithreadingSiteVisitor(synchronizedBlockCounter);
        MultithreadingSiteVisitor unSynchVisitor = new MultithreadingSiteVisitor(unSynchronizedCounter);
        MultithreadingSiteVisitor volatileVisitor = new MultithreadingSiteVisitor(volatileCounter);

        atomicVisitor.visitMultithread(100, "atomicVisitor");
        lockVisitor.visitMultithread(100, "lockVisitor");
        synchVisitor.visitMultithread(100, "synchVisitor");
        unSynchVisitor.visitMultithread(100, "unSynchVisitor");
        volatileVisitor.visitMultithread(100, "volatileVisitor");
    }
}

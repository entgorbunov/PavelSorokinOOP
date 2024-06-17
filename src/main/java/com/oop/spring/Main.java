package com.oop.spring;

import com.oop.spring.service.OperationsConsoleListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OperationsConsoleListener listener = context.getBean(OperationsConsoleListener.class);
        listener.start();
    }
}

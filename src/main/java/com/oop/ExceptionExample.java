package com.oop;

public class ExceptionExample {
    public static void main(String[] args) {
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Произошла арифметическая ошибка: " + e.getMessage());
        } finally {
            System.out.println("Блок деления завершен.");
        }

        try {
            int[] array = new int[5];
            int number = array[10];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ошибка доступа к массиву: " + e.getMessage());
        } finally {
            System.out.println("Блок доступа к массиву завершен.");
        }
    }
}

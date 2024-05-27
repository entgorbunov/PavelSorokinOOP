package com.oop;

import java.io.*;

public class FileReadExample {
    private static final String SOURCE_FILE = "src/main/resources/exceptions/source.txt";
    private static final String DESTINATION_FILE = "src/main/resources/exceptions/destination.txt";

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(SOURCE_FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода при работе с файлом - " + e.getMessage());
        }


        try (FileWriter fileWriter = new FileWriter(DESTINATION_FILE);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода при записи в файл - " + e.getMessage());
        }

        System.out.println("Данные успешно записаны в файл destination.txt");
    }
}

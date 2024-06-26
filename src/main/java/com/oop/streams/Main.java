package com.oop.streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Product book1 = new Product(1L, "Основы Джава", "Книги", new BigDecimal("120"));
        Product book2 = new Product(2L, "Спринг", "Книги", new BigDecimal("150"));
        Product toy1 = new Product(3L, "Игра для приставки", "Игрушки", new BigDecimal("200"));

        Product childProduct1 = new Product(4L, "Кукла", "Детские игрушки", new BigDecimal("80"));
        Product childProduct2 = new Product(5L, "Машинка", "Детские играшки", new BigDecimal("60"));
        Product toy2 = new Product(6L, "Игра для ПК", "Игрушки", new BigDecimal("90"));

        Order order1 = new Order(1L, LocalDate.of(2021, 3, 15), LocalDate.of(2021, 3, 20), "Доставлен", new HashSet<>(Arrays.asList(book1, toy1)));
        Order order2 = new Order(2L, LocalDate.of(2021, 2, 14), LocalDate.of(2021, 2, 20), "Доставлен", new HashSet<>(Arrays.asList(book2, childProduct1)));
        Order order3 = new Order(3L, LocalDate.of(2021, 3, 14), LocalDate.of(2021, 3, 18), "Доставлен", new HashSet<>(Arrays.asList(childProduct2, toy2)));

        Customer customer1 = new Customer(1L, "Василий", 2L, new HashSet<>(Arrays.asList(order1, order2, order3)));

        List<Customer> customers = new ArrayList<>(Arrays.asList(customer1));
        //Получите список продуктов из категории "Books" с ценой более 100.
        List<Product> expensiveBooks = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Книги") && product.getPrice().compareTo(new BigDecimal("100")) > 0)
                .distinct()
                .toList();
        //Получите список заказов с продуктами из категории "Children's products".
        List<Order> ordersWithChildrenProducts = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getProducts().stream().anyMatch(product -> product.getCategory().equals("Детские игрушки")))
                .distinct()
                .toList();
        //Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех продуктов.
        BigDecimal sumOfDiscountedToys = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Игрушки"))
                .map(product -> product.getPrice().multiply(new BigDecimal("0.90")))
                .distinct()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        List<Product> productsOrderedByLevel2 = customers.stream()
                .filter(customer -> customer.getLevel() == 2)
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> !order.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)) && !order.getOrderDate().isAfter(LocalDate.of(2021, 4, 1)))
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .toList();


        //Получите топ 2 самые дешевые продукты из категории "Books".
        List<Product> cheapestTwoBooks = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Книги"))
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .distinct()
                .toList();

        // Получите 3 самых последних сделанных заказа.
        List<Order> lastThreeOrders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();

        // Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните список их продуктов.
        List<Product> productsFromOrdersOnMarch15 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println("Номер заказа: " + order.getId()))
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .toList();

        // Рассчитайте общую сумму всех заказов, сделанных в феврале 2021.
        BigDecimal totalFebruaryOrders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().getMonthValue() == 2 && order.getOrderDate().getYear() == 2021)
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .distinct()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        OptionalDouble averageMarch14Orders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 14)))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(product -> product.getPrice().doubleValue())
                .distinct()
                .average();

        // Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех продуктов категории "Книги".
        DoubleSummaryStatistics bookStats = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Книги"))
                .mapToDouble(product -> product.getPrice().doubleValue())
                .distinct()
                .summaryStatistics();

        // Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
        Map<Long, Integer> orderProductCount = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(Order::getId, order -> order.getProducts().size()));

        // Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов
        Map<Customer, List<Order>> customerOrdersMap = customers.stream()
                .collect(Collectors.toMap(Function.identity(), customer -> new ArrayList<>(customer.getOrders())));

        // Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        Map<Order, Double> orderTotalMap = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(Function.identity(), order -> order.getProducts().stream()
                        .mapToDouble(product -> product.getPrice().doubleValue()).sum()));

        // Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        Map<String, List<String>> categoryProductNames = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.mapping(Product::getName, Collectors.toList())));

        // Получите Map<String, Product> → самый дорогой продукт по каждой категории.
        Map<String, Product> mostExpensiveByCategory = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toMap(Product::getCategory, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(Product::getPrice))));


        System.out.println("Дорогие книги: " + expensiveBooks);
        System.out.println("Заказы с детскими товарами: " + ordersWithChildrenProducts);
        System.out.println("Сумма скидок на игрушки: " + sumOfDiscountedToys);
        System.out.println("Продукты, заказанные клиентами второго уровня: " + productsOrderedByLevel2);
        System.out.println("Две самые дешевые книги: " + cheapestTwoBooks);
        System.out.println("Последние три заказа: " + lastThreeOrders);
        System.out.println("Продукты из заказов, сделанных 15 марта: " + productsFromOrdersOnMarch15);
        System.out.println("Общая сумма заказов в феврале: " + totalFebruaryOrders);
        System.out.println("Средний платеж по заказам, сделанным 14 марта: " + (averageMarch14Orders.isPresent() ? averageMarch14Orders.getAsDouble() : "Нет заказов"));
        System.out.println("Статистика по книгам: " + bookStats);
        System.out.println("Количество продуктов в заказе: " + orderProductCount);
        System.out.println("Карта заказов клиентов: " + customerOrdersMap);
        System.out.println("Карта общей суммы заказов: " + orderTotalMap);
        System.out.println("Карта названий продуктов по категориям: " + categoryProductNames);
        System.out.println("Самый дорогой продукт по каждой категории: " + mostExpensiveByCategory);
    }
}

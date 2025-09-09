package com.mj.async.completableFuture;

import com.mj.async.dto.Product;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class AcceptRunApplyDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        simpleDemo();
        dispatchProcess();
        dispatchProcessAsync();
    }

    //    this will run in thread of the customised pool, we can also pass this executors with run apply and accept methods
    public static void dispatchProcessAsync() throws ExecutionException, InterruptedException {
        System.out.println("**********************************************************");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture.supplyAsync(() -> {
            System.out.println("get all products : " + Thread.currentThread().getName());
            return fetchProducts();
        }, executor).thenApplyAsync(products -> {
            System.out.println("filter category : " + Thread.currentThread().getName());
            return products.stream().filter(product -> "cloth".equalsIgnoreCase(product.getCategory())).collect(Collectors.toList());
        }, executor).thenApplyAsync(products -> {
            System.out.println("filter qty : " + Thread.currentThread().getName());
            return products.stream().filter(product -> product.getQty() >= 20).collect(Collectors.toList());
        }, executor).thenAcceptAsync(products -> {
            System.out.println("dispatch : " + Thread.currentThread().getName());
            products.forEach(System.out::println);
        }, executor).thenRunAsync(() -> {
            System.out.println("complete : " + Thread.currentThread().getName());
            System.out.println("TaskCompleted");
        }, executor).get();
    }

    // this will run in a single thread and not asynchronous
    public static void dispatchProcess() throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("get all products : " + Thread.currentThread().getName());
            return fetchProducts();
        }).thenApply(products -> {
            System.out.println("filter category : " + Thread.currentThread().getName());
            return products.stream().filter(product -> "cloth".equalsIgnoreCase(product.getCategory())).collect(Collectors.toList());
        }).thenApply(products -> {
            System.out.println("filter qty : " + Thread.currentThread().getName());
            return products.stream().filter(product -> product.getQty() >= 20).collect(Collectors.toList());
        }).thenAccept(products -> {
            System.out.println("dispatch : " + Thread.currentThread().getName());
            products.forEach(System.out::println);
        }).thenRun(() -> {
            System.out.println("complete : " + Thread.currentThread().getName());
            System.out.println("TaskCompleted");
        }).get();
    }

    private static void simpleDemo() {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApplyAsync(String::toUpperCase)
                .thenAcceptAsync(System.out::println)
                .thenRunAsync(() -> System.out.println("task completed")).join();

        // we must make use of get(), because asynchronous thread will be running in deamon or different theread,
//        in which the main method will not be knowing so it will run and exit from JVM, so we need to make used of get()
        // it will hold the main method until its asynchronous operations finishes or we can also make use of join()- join will wait for all the asynchronous thread to finish it's task
    }


    public static List<Product> fetchProducts() {
        return Arrays.asList(
                new Product(1, "Apple", "Food", 50),
                new Product(2, "Jeans", "Cloth", 20),
                new Product(3, "Milk", "Liquids", 30),
                new Product(4, "Iron Rod", "Metals", 15),
                new Product(5, "Rose", "Flowers", 100),
                new Product(6, "Laptop", "Electronics", 10),
                new Product(7, "Shampoo", "Cosmetics", 25),
                new Product(8, "Bread", "Food", 40),
                new Product(9, "T-Shirt", "Cloth", 35),
                new Product(10, "Orange Juice", "Liquids", 20),
                new Product(11, "Copper Wire", "Metals", 50),
                new Product(12, "Tulip", "Flowers", 60),
                new Product(13, "Smartphone", "Electronics", 15),
                new Product(14, "Conditioner", "Cosmetics", 30),
                new Product(15, "Butter", "Food", 25)
        );
    }
}

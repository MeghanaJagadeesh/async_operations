package com.mj.async.completableFuture;

import com.mj.async.dto.Product;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SupplyAsyncDemo {
//    whenever we required the result of the asynchronous operation, we need to go with supplyAsync

    static List<Product> products = Arrays.asList(
            new Product(1, "abc", "food", 10),
            new Product(2, "xyz", "cloth", 20),
            new Product(3, "mno", "liquids", 15),
            new Product(4, "tyu", "metals", 5),
            new Product(5, "qwe", "flowers", 30)
    );

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();

        //        supplyAsync with default thread pool
        supplyAsyncDemo.fetchProduct().forEach(System.out::println);

//        supplyAsync with customized thread pool
        supplyAsyncDemo.fetchProductWithExecutor().forEach(System.out::println);

    }

    private List<Product> fetchProductWithExecutor() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<List<Product>> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread = " + Thread.currentThread().getName());
            return fetch();
        }, executor);

//        if we make use of futute.get() method this will block the main thread until it's thread finishes the task
        return future.get();

    }

    public List<Product> fetchProduct() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Product>> future = CompletableFuture.supplyAsync(this::fetch);
        return future.get();
    }

    public List<Product> fetch() {
        return products;
    }

}

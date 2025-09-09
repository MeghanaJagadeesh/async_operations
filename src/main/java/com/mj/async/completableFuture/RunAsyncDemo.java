package com.mj.async.completableFuture;

import com.mj.async.dto.Product;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunAsyncDemo {

    // runasync method will be used whenever we don't want any response after the thread gets executed
//  signature:  CompleteAbleFuture.runAsync(Runnable)
//    CompleteAbleFuture.runAsync(Runnable, Executor)


//  if we use runAsync.get() it will block main thread until this thread get finishes, even though it runs in different thread it will not be asynchronous

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Product> products = Arrays.asList(
                new Product(1, "abc", "food", 10),
                new Product(2, "xyz", "cloth", 20),
                new Product(3, "mno", "liquids", 15),
                new Product(4, "tyu", "metals", 5),
                new Product(5, "qwe", "flowers", 30)
        );
//    CompleteAbleFuture.runAsync(Runnable)
        saveProduct(products);

//        CompleteAbleFuture.runAsync(Runnable, Executor)
        CompletableFuture<Void> voids = saveProductWithExecutor(products);
    }


    //    using lambda
    private static CompletableFuture<Void> saveProductWithExecutor(List<Product> products) {

        ExecutorService executors = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            // repository.save()
            // products.forEach(product->repository.save());
            System.out.println("Thread = " + Thread.currentThread().getName());
            System.out.println(products.size());
        }, executors);

//        the above code will eun in any of the thread in the initialized pool,
//        below code will run in the main thread
        System.out.println("######");
        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
        }
        return runAsync;
    }

    private static void saveProduct(List<Product> products) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> runAysnc = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                // repository.save()
                // products.forEach(product->repository.save());
                System.out.println("Thread = " + Thread.currentThread().getName());
                products.forEach(System.out::println);
                System.out.println(products.size());
            }
        });
        runAysnc.get(); // if we use runAsync.get it will block main thread until this thread get finishes,
        System.out.println("**************");
    }
}

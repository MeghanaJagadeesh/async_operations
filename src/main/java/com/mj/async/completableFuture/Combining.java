package com.mj.async.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Combining {

//    in combine mainly we have 4 methods
//    themCombine() -  whenever we want the combine the result of two independent tasks
//    thenCompose() - whenever we want to run the second task based on the result of first task
//    allOf() - It will wait for all the tasks to complete
//    anyOf() - It will return the first completed task

    public static void main(String[] args) {
        thenCombine();
        thenCompose();
        allOfDemo();
        anyOfDemo();
    }

    private static void anyOfDemo() {
        CompletableFuture<Integer> fast = CompletableFuture.supplyAsync(() -> 101);
        CompletableFuture<Integer> slow = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 102;
        });

        CompletableFuture<Object> first = CompletableFuture.anyOf(fast, slow);
        first.thenAccept(result -> System.out.println("First completed: " + result));
    }

    private static void allOfDemo() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 101);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 102);
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> 103);

        CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2, future3);
        all.thenRun(() -> System.out.println("All task Completed"));
    }

    private static void thenCompose() {
        CompletableFuture<Integer> userId = CompletableFuture.supplyAsync(() -> 101);
        CompletableFuture<Object> user = userId.thenCompose(id -> {
//            fetchUser(id);
            System.out.println("user fetch");
            return null;
        });
    }

//    private static CompletionStage<Object> fetchUser(Integer id) {
//    }

    public static void thenCombine() {
        CompletableFuture<Integer> price = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Integer> tax = CompletableFuture.supplyAsync(() -> 20);
        CompletableFuture<Integer> total = price.thenCombine(tax, (p, t) -> {
            System.out.println("price = " + price + "\ntax = " + tax);
            return p + t;
        });
        total.thenAccept(result -> System.out.println("total = " + result));
    }
}

package com.mj.async.exception;

import java.util.concurrent.CompletableFuture;

public class ExceptionHandling {

//    Exception Handling is one of the important context in async, because it won't behave in a normal exception handler

//    1. exceptionally() - it just like a catch where we can handle the exception
//    2. handle() -  it is like try-catch-finally, it will execute even if it fails or gives result
//    3. whenComplete() -  this is mainly used for logging or cleanup
//    4. completeExceptionally() - we can manually complete the future with error

    public static void main(String[] args) {
        exceptionally();
        handle(110);
        whenComplete();
    }

    //    behaves like a catch
    public static void exceptionally() {
        CompletableFuture<Integer> exc = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("i thrown it");
            return 10;
        });
        exc.exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return 10;
        }).thenAccept(result -> System.out.println("result = " + result));
    }

    //    behaves like try-catch-throws;
    public static void handle(int num) {
        CompletableFuture.supplyAsync(() -> {
            if (num > 10) {
                throw new RuntimeException("exception : num is greater ");
            }
            return num;
        }).handle((result, ex) -> {
            if (ex != null) {
                System.out.println(ex.getMessage());
            }
            return result;
        }).thenAccept(System.out::println);
    }

    public static void whenComplete() {
        CompletableFuture<Integer> exc = CompletableFuture.supplyAsync(() -> 5 / 0);
        exc.whenComplete((res, ex) -> {
            if (ex != null) {
                System.out.println(ex.getMessage());
            } else {
                System.out.println("run successfully");
            }
        }).thenAccept(System.out::println);
    }
}

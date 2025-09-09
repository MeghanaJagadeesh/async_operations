package com.mj.async.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Hello, Future!";
        });
        try {
            String result = future.get();
            System.out.println("result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        test(executorService);
    }

//    drawbacks
//    1. future has limited metod, so we cannot stop the execution in between whenever it takes more time
//    2. whenever we have multiple future can cannot be chained together
//    3. whenever we have multiple future we cannot combine it to gether
//    4. No proper exception handling

    public static void test(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Future<List<Integer>> future1 = executorService.submit(() -> {
            return Arrays.asList(1, 2, 3, 4);
        });

        Future<List<Integer>> future2 = executorService.submit(() -> {
            int a = 10;
            try {
                int b = a / 0;
            } catch (Exception e) {
                System.out.println("exception occurs");
            }
            return Arrays.asList(1, 2, 3, 4);

        });

        Future<List<Integer>> future3 = executorService.submit(() -> {
            return Arrays.asList(1, 2, 3, 4);
        });

//        future1.future2 not possible each we need to get separetely
        List<Integer> result = future1.get();
        System.out.println("res= " + result);
        List<Integer> result2 = future2.get();
        System.out.println("result2 = " + result2);
//        result.add(100); not possible we cannot modify it again
//        System.out.println(result);

    }

}

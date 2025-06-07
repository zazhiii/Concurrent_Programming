package com.zazhi.P08.C06_Handle_Exception;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Test {

    static ExecutorService pool = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        f1(); // 手动处理异常
        f2(); // Future.get() 方法处理异常
    }

    // 手动处理异常
    public static void f1(){
        pool.submit(() ->{
            try {
                int i = 1 / 0; // 故意制造异常
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void f2() throws ExecutionException, InterruptedException {
        Future<Integer> future = pool.submit(() -> {
            return 1 / 0;
        });

        System.out.println(future.get());

    }
}

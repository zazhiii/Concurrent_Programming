package com.zazhi.P08.C02_ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

@Slf4j
public class TestInvokeAny {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
         ExecutorService pool = Executors.newFixedThreadPool(3);

         List<Callable<String>> tasks = new ArrayList<>();

         for (int i = 1; i <= 3; i++) {
             int finalI = i;
             tasks.add(() -> {
                 log.debug("start task {}", finalI);
                 sleep(1000 * finalI);
                 return "ok" + finalI;
             });
         }

         // 提交任务
         // invokeAny()方法会返回第一个完成的任务的结果
         var future = pool.invokeAny(tasks);

         log.debug("{}", future);
    }
}

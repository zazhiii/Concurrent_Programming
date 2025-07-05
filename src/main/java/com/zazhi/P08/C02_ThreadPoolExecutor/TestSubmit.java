package com.zazhi.P08.C02_ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.zazhi.C04.park_unpark.P08_Park_Unpark.sleep;

@Slf4j
public class TestSubmit {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(2);

        Callable<String> call = () -> {
            log.debug("start task ...");
            sleep(1000);
            return "ok";
        };

        // 提交任务
        var future = pool.submit(call);

        // 获取结果
        log.debug("{}", future.get());

    }
}

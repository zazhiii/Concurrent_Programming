package com.zazhi.P08.C02_ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.zazhi.util.SleepUtil.sleep;

@Slf4j
public class TestInvokeAll {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        List<Callable<String>> tasks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            tasks.add(() -> {
                log.debug("start task {}", finalI);
                sleep(1000);
                return "ok" + finalI;
            });
        }

        // 提交任务
        var futures = pool.invokeAll(tasks);

        // 获取结果
        // 这里会阻塞，直到所有任务完成
        for (var future : futures) {
            try {
                log.debug("{}", future.get());
            } catch (Exception e) {
                log.error("error", e);
            }
        }

    }
}

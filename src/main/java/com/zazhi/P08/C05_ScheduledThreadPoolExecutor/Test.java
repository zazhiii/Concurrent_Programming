package com.zazhi.P08.C05_ScheduledThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.zazhi.util.SleepUtil.sleep;

@Slf4j
public class Test {
    static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
    public static void main(String[] args) {
        // f1(); // 定时任务
        // f2(); // 周期性任务，使用 scheduleAtFixedRate
        f3(); // 周期性任务，使用 scheduleWithFixedDelay
    }
    public static void f1(){
        // 定时任务
        pool.schedule(() -> {
            log.debug("运行任务1 ");
            int i = 1 / 0; // 故意抛出异常
        }, 1, TimeUnit.SECONDS);

        pool.schedule(() -> {
            log.debug("运行任务2 "); // 这个任务会在任务1抛出异常后继续执行
        }, 2, TimeUnit.SECONDS);
    }

    public static void f2(){
        // 周期性任务
        pool.scheduleAtFixedRate(() -> {
            log.debug("运行任务3 ");
            sleep(2000); // 故意让任务3执行时间超过周期 -- 会压缩掉间隔，但任务不会重叠
        }, 3, 1, TimeUnit.SECONDS);
    }

    public static void f3(){
        // 周期性任务
        pool.scheduleWithFixedDelay(() -> {
            log.debug("运行任务4 ");
            sleep(2000); // 不会压缩间隔，上一次任务结束和下一次任务开始之间会间隔
        }, 3, 1, TimeUnit.SECONDS);
    }
}

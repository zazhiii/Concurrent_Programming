package com.zazhi.P08.C01_custom_thread_pool;

import com.zazhi.P08.C01_custom_thread_pool.reject_policy.CallerRunsPolicy;

import java.util.concurrent.TimeUnit;

import static com.zazhi.util.SleepUtil.sleep;

/**
 * @author zazhi
 * @date 2025/5/15
 * @description: TODO
 */
public class Test {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(
                1,
                1000,
                TimeUnit.MILLISECONDS,
                1,
//                new WaitForeverRejectPolicy<>()
//                new WaitTimeoutRejectPolicy<>(1000, TimeUnit.MILLISECONDS)
//                new DiscardPolicy<>()
//                new AbortPolicy<>()
                new CallerRunsPolicy<>()
        );

        for (int i = 0; i < 3; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                sleep(3000);
                System.out.println(Thread.currentThread().getName() + "Task " + finalI + " is running");
            });
        }
    }
}

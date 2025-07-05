package com.zazhi.C04;

import lombok.extern.slf4j.Slf4j;

import static com.zazhi.util.SleepUtil.sleep;

/**
 * @author zazhi
 * @date 2025/4/29
 * @description: 活锁
 */
@Slf4j
public class P10_LiveLock {
    private static int count = 10;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            while (count > 0) {
                sleep(1000);
                log.info(" count: " + count);
                count--;
            }
        }, "t1").start();

        new Thread(() -> {
            while (count < 20) {
                sleep(1000);
                log.info(" count: " + count);
                count++;
            }
        }, "t2").start();
    }
}

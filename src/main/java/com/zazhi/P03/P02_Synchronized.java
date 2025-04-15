package com.zazhi.P03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/15
 * @description: synchronized 关键字
 */
@Slf4j
public class P02_Synchronized {

    static int count = 0;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 10000; i ++){
                synchronized (lock) {
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (lock) {
                    count--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        log.debug("count: {}", count); // count: 0
    }
}

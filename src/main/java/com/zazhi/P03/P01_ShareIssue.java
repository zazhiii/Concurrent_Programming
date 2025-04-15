package com.zazhi.P03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/15
 * @description: 共享变量带来的问题
 */
@Slf4j
public class P01_ShareIssue {

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 10000; i ++){
                count++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count --;
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        log.debug("count: {}", count); // count的值可能不是0
    }
}

package com.zazhi.P03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zazhi
 * @date 2025/4/26
 * @description: park - unpark 的使用
 *
 * unpark() 可以在 park() 之前调用, 也可以在 park() 之后调用
 *
 */
@Slf4j
public class P08_Park_Unpark {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("t1, 开始执行");
            sleep(2000);
            log.debug("t1, park...");
            LockSupport.park();
            log.debug("t1, resume...");
        }, "t1");
        t1.start();

        sleep(1000);

        log.debug("主线程, unpark t1");
        LockSupport.unpark(t1);
    }
}

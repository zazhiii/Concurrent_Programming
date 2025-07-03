package com.zazhi.C03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/15
 * @description: 线程状态
 */
@Slf4j
public class P04_ThreadState {
    public static void main(String[] args) {
        // NEW
        Thread t1 = new Thread(() -> {
        }, "t1");

        // RUNNABLE
        Thread t2 = new Thread(() -> {
            while (true) {

            }
        }, "t2");
        t2.start();

        // TERMINATED
        Thread t3 = new Thread(() -> {

        }, "t3");
        t3.start();

        // TIMED_WAITING
        Thread t4 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t4");
        t4.start();

        // WAITING
        Thread t5 = new Thread(() -> {
            synchronized (P04_ThreadState.class) {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t5");
        t5.start();

        // BLOCKED
        Thread t6 = new Thread(() -> {
            synchronized (P04_ThreadState.class) {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t6");
        t6.start();

        log.debug("t1 State: {}", t1.getState()); // NEW
        log.debug("t2 State: {}", t2.getState()); // RUNNABLE
        log.debug("t3 State: {}", t3.getState()); // TERMINATED
        log.debug("t4 State: {}", t4.getState()); // TIMED_WAITING
        log.debug("t5 State: {}", t5.getState()); // WAITING
        log.debug("t6 State: {}", t6.getState()); // BLOCKED

    }
}

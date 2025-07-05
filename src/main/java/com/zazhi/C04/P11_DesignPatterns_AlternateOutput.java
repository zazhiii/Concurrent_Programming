package com.zazhi.C04;

import lombok.extern.slf4j.Slf4j;

import static com.zazhi.util.SleepUtil.sleep;

/**
 * @author zazhi
 * @date 2025/5/1
 * @description: 设计模式 -- 交替输出
 */
@Slf4j
public class P11_DesignPatterns_AlternateOutput {

    private static int num = 0;

    private static final Object lock = new Object();

    private static char[] ch = {'a', 'b', 'c'};

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    log.debug("线程1: {}", ch[num % 3]);
                    num++;

                    sleep(1000);

                    lock.notify(); // 唤醒其他线程
                    while (num % 2 == 1) {
                        wait(lock);
                    }
                }
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    while (num % 2 == 0) {
                        wait(lock);
                    }
                    log.debug("线程2: {}", ch[num % 3]);
                    num++;

                    sleep(1000);

                    lock.notify(); // 唤醒其他线程
                }
            }
        }, "t2").start();
    }

    public static void wait(Object obj) {
        try {
            obj.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

package com.zazhi.C04;

import lombok.extern.slf4j.Slf4j;

import static com.zazhi.util.SleepUtil.sleep;

/**
 * @author zazhi
 * @date 2025/4/29
 * @description: 设计模式 -- 固定执行顺序
 *
 *  先执行线程2, 再执行线程1
 *
 *   三种实现方式:
 *  1. synchronized
 *  2. park/unpark
 *  3. Reentrantlock
 */
@Slf4j
public class P11_DesignPatterns_FixedExecutionOrder {

    static Object lock = new Object();
    static boolean isT2Finished = false;

    public static void main(String[] args) {

        new Thread(() -> {
            sleep(1000);
            synchronized (lock) {
                while (!isT2Finished) {
                    try {
                        lock.wait(); // 等待线程2的通知
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                log.debug("线程1: 继续执行");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (lock) {
                log.debug("线程2: 开始执行");
                isT2Finished = true; // 设置线程2执行完成
                lock.notify(); // 唤醒线程1
            }
        }, "t2").start();
    }
}

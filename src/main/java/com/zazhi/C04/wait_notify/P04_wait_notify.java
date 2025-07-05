package com.zazhi.C04.wait_notify;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/18
 * @description: wait/notify 机制
 */
@Slf4j
public class P04_wait_notify {

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("t1: 等待中...");
                    lock.wait(); // 释放锁，等待通知
                    log.debug("t1: 被唤醒了");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("t2: 等待中...");
                    lock.wait(); // 释放锁，等待通知
                    log.debug("t2: 被唤醒了");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "t2");

        t1.start();
        t2.start();

        Thread.sleep(1000); // 确保 t1 和 t2 都在等待中

        synchronized (lock) {
            log.debug("主线程: 唤醒 t1 和 t2");
//            lock.notify(); // 唤醒一个等待的线程
            lock.notifyAll(); // 唤醒所有等待的线程
        }

    }
}

package com.zazhi.P03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/18
 * @description: wait/notify 机制
 */
@Slf4j
public class P05_How_to_use_wait_notify_02 {
    private static final Object room = new Object();
    private static boolean hasCigarette = false;
    private static boolean hasTakeout = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                while (!hasCigarette) {
                    try {
                        log.debug("小明: 等待香烟...");
                        room.wait(); // 正确姿势：也可以等待直到被送烟线程唤醒
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                log.debug("有烟吗?[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("小明可以干活了");
                }
            }
        }, "小明").start();

        new Thread(() -> {
            synchronized (room) {
                while (!hasTakeout) {
                    try {
                        log.debug("小红: 等待外卖...");
                        room.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                log.debug("有外卖吗?[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("小红可以干活了");
                }
            }
        }, "小红").start();

        Thread.sleep(1000);
        new Thread(() -> {
            synchronized (room) {
                log.debug("老板: 给小明香烟");
                hasCigarette = true;
                room.notifyAll(); // 唤醒等待的线程
            }
        }, "老板").start();

        Thread.sleep(2000);
        new Thread(() -> {
            synchronized (room) {
                log.debug("老板: 给小红外卖");
                hasTakeout = true;
                room.notifyAll(); // 唤醒等待的线程
            }
        }, "老板").start();
    }
}

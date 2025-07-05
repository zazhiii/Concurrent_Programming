package com.zazhi.C04.wait_notify;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/18
 * @description: wait/notify 机制
 */
@Slf4j
public class P05_How_to_use_wait_notify {

    private static final Object room = new Object();
    private static boolean hasCigarette = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                while (!hasCigarette) {
                    try {
                        log.debug("小明: 等待香烟...");
                        // 模拟等待香烟的时间
//                            Thread.sleep(2000); // 错误姿势：sleep过程中锁不会被释放，其他人不能获取锁
//                        room.wait(2000); // 正确姿势：wait会释放锁，其他人可以在等待过程中获取锁并执行
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

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    log.debug("其他人干活了");
                }
            }, "其他人" + i).start();
        }

        Thread.sleep(1000); // 确保小明线程已经开始等待

        new Thread(() -> {
            synchronized (room) {
                log.debug("老板: 给小明香烟");
                hasCigarette = true;
                room.notify(); // 唤醒一个等待的线程
            }
        }, "老板").start();


    }
}

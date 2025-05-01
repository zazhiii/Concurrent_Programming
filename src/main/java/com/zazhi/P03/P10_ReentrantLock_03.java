package com.zazhi.P03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.zazhi.util.SleepUtil.sleep;

/**
 * @author zazhi
 * @date 2025/4/29
 * @description: 锁超时
 */
@Slf4j
public class P10_ReentrantLock_03 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("尝试获取锁");
            try {
                if (!lock.tryLock(1, TimeUnit.SECONDS)) {
                    log.debug("没获取到锁");
                    return;
                }
            } catch (InterruptedException e) {
                log.debug("没获取到锁，被打断");
                return;
            }
            try {
                log.debug("get lock");
            } finally {
                lock.unlock();
            }

        }, "t1");

        log.debug("加锁");
        lock.lock();
        t1.start();
        sleep(500);
        log.debug("解锁");
        lock.unlock();

    }

}



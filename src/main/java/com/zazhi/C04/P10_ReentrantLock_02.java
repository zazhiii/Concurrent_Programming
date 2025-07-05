package com.zazhi.C04;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.locks.ReentrantLock;

import static com.zazhi.util.SleepUtil.sleep;

/**
 * @author zazhi
 * @date 2025/4/29
 * @description: 可打断
 */
@Slf4j
public class P10_ReentrantLock_02 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                log.debug("尝试获取锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("没获取到锁，打断");
                return;
            }
            try {
                log.debug("get lock");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        t1.start();

        sleep(1000);

        t1.interrupt();
    }

}



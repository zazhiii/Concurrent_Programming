package com.zazhi.C04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.zazhi.util.SleepUtil.sleep;

/**
 * @author zazhi
 * @date 2025/4/29
 * @description: 条件变量
 */
@Slf4j
public class P10_ReentrantLock_05 {

    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    static ReentrantLock ROOM = new ReentrantLock();
    static Condition waitCigaretteSet = ROOM.newCondition();
    static Condition waitTakeoutSet = ROOM.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("开始等待烟");
                while (!hasCigarette) {
                    log.debug("没有烟，等待");
                    waitCigaretteSet.await();
                }
                log.debug("有烟了，开始抽烟");
            } catch (InterruptedException e) {
                log.debug("等待烟被中断");
            } finally {
                ROOM.unlock();
            }
        }, "等待烟").start();

        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("开始等待外卖");
                while (!hasCigarette) {
                    log.debug("没有外卖，等待");
                    waitTakeoutSet.await();
                }
                log.debug("有外卖了，开始吃外卖");
            } catch (InterruptedException e) {
                log.debug("等待外卖被中断");
            } finally {
                ROOM.unlock();
            }
        }, "等待外卖").start();

        sleep(1000);

        new Thread(() -> {
            ROOM.tryLock();
            try {
                hasCigarette = true;
                log.debug("送烟来了");
                waitCigaretteSet.signalAll();
            }finally {
                ROOM.unlock();
            }
        }, "送烟的").start();

        sleep(1000);

        new Thread(() -> {
            ROOM.tryLock();
            try {
                hasCigarette = true;
                log.debug("送外卖来了");
                waitTakeoutSet.signalAll();
            }finally {
                ROOM.unlock();
            }
        }, "送外卖的").start();
    }
}



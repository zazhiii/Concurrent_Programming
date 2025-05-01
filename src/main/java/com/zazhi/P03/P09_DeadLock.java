package com.zazhi.P03;

import com.zazhi.util.SleepUtil;

/**
 * @author zazhi
 * @date 2025/4/29
 * @description: 死锁
 */
public class P09_DeadLock {
    private static final Object A = new Object();
    private static final Object B = new Object();
    public static void main(String[] args) {
        new Thread(() ->{
            synchronized (A) {
                System.out.println("t0 get A");
                SleepUtil.sleep(1000);
                synchronized (B) {
                    System.out.println("t0 get B");
                }
            }
        }, "t0").start();

        new Thread(() ->{
            synchronized (B) {
                System.out.println("t1 get B");
                SleepUtil.sleep(1000);
                synchronized (A) {
                    System.out.println("t1 get A");
                }
            }
        }, "t1").start();
    }
}

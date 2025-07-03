package com.zazhi.C03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zazhi
 * @date 2025/4/14
 * @description: 常用方法
 */
@Slf4j
public class P02_CommonMethods {
    public static void main(String[] args){
//        f1();
//        f2();
//        f3();
//        f4();
//        f5();
//        f6();
//        f7();
//        f8();
        f9();

    }

    // 1. start() 启动线程
    // 2. getState() 获取线程状态
    public static void f1() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println("t1 is running");
            }
        };
        log.debug("Thread State: {}", t1.getState()); // NEW
        t1.start();
        log.debug("Thread State: {}", t1.getState()); // RUNNABLE
    }

    // 3. sleep() 线程休眠
    public static void f2() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.debug("t1 is running");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("Thread State: {}", t1.getState()); // TIMED_WAITING
    }

    // 4. interrupt() 中断线程
    public static void f3(){
        Thread t1 = new Thread(() -> {
            log.debug("t1 is running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.debug("t1 interrupted");
            }
        }, "t1");

        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.debug("interrupt t1");
        t1.interrupt(); // 中断线程
    }

    // 5. yeild() 让出 CPU
    // 6. setPriority() 设置优先级
    public static void f4(){
        Runnable r1 = () -> {
            int count = 0;
            while(true){
                System.out.println("--> r1: " + count++);
            }
        };
        Runnable r2 = () -> {
            int count = 0;
            while(true){
//                Thread.yield(); // 让出 CPU
                System.out.println("        --> r2: " + count++);
            }
        };
        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");
        t1.setPriority(Thread.MAX_PRIORITY); // 设置优先级 只在 CPU 资源紧张时生效
        t2.setPriority(Thread.MIN_PRIORITY); // 设置优先级
        t1.start();
        t2.start();
    }

    // 7. join(): 等待线程结束
    public static int r = 0;
    public static void f5(){
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("结束");
            r = 10;
        }, "t1");
        t1.start();
        try {
            t1.join(); // 等待 t1 结束
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("r: {}", r);
        log.debug("结束");
    }

    // 打断睡眠线程
    public static void f6(){
        Thread t1 = new Thread(() -> {
            log.debug("t1 is running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.debug("t1 interrupted");
            }
        }, "t1");
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("interrupt t1");
        t1.interrupt();
        log.debug("打断标记: {}", t1.isInterrupted());

    }

    // 打断正常运行线程
    public static void f7(){
        Thread t1 = new Thread(() -> {
            log.debug("t1 is running");
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();// 判断是否被打断
                // 调用 t1.interrupt() 方法会将 interrupted 设置为 true
                // 但不会终止 t1 线程的正常运行
                // 由被打断线程自己决定是否退出
                if (interrupted) {
                    log.debug("t1 interrupted");
                    break; // 退出循环
                }
            }
        }, "t1");
        t1.start();
        t1.interrupt();
        log.debug("打断标记: {}", t1.isInterrupted()); // true
    }

    // 打断park
    public static void f8() {
        Thread t1 = new Thread(() -> {
            Thread currentThread = Thread.currentThread();

            log.debug("t1 is running");
            LockSupport.park(); // 阻塞线程
            log.debug("t1 unparked, 打断标记: {}", currentThread.isInterrupted());

            Thread.interrupted(); // 该方法会返回打断标记 并 重置打断标记
            log.debug("打断标记: {}", currentThread.isInterrupted());
            LockSupport.park(); // 若打断标记为 true 调用 park() 则不会阻塞
            log.debug("unpark...");

        }, "t1");

        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        t1.interrupt();
        LockSupport.unpark(t1);
    }

    // 过时的方法: 1. stop() 2. suspend() 3. resume()

    // 守护线程
    public static void f9(){
        Thread t1 = new Thread(() -> {
            while(true){
                log.debug("守护线程正在运行");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "deamon");
        t1.setDaemon(true); // 设置为守护线程
        t1.start();
        log.debug("main thread is running");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("main thread is finished");
    }
}

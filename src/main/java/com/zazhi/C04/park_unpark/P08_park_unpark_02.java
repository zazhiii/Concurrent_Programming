package com.zazhi.C04.park_unpark;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zazhi
 * @date 2025/7/5
 * @description: park - unpark 的使用示例
 *
 *  第二次才 park 住
 *
 */
public class P08_park_unpark_02 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("t1, 开始执行");

            System.out.println("第一次park...");
            LockSupport.park();
            System.out.println("t1, resume...");

            System.out.println("第二次park...");
            LockSupport.park();
            System.out.println("t1, resume again...");
        }, "t1");

        LockSupport.unpark(t1);

        t1.start();

        LockSupport.unpark(t1);
    }
}

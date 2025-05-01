package com.zazhi.P04;

import lombok.extern.slf4j.Slf4j;

import static com.zazhi.util.SleepUtil.sleep;

/**
 * @author zazhi
 * @date 2025/5/1
 * @description: 可见性
 */
@Slf4j
public class P01_Visibility {

//    static boolean f = false;  // t1线程无法停止

    volatile static boolean f = false;  // t1线程可以停止

    // volatile关键字可以保证可见性

    // 对 f 加锁也可以保证他的可见性

    public static void main(String[] args) {
        new Thread(() -> {
            while (!f) {

            }
        }, "t1").start();

        sleep(2000);
        log.debug("停止t1");
        f = true;
    }
}

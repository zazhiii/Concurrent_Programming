package com.zazhi.util;

/**
 * @author zazhi
 * @date 2025/4/29
 * @description: TODO
 */
public class SleepUtil {
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

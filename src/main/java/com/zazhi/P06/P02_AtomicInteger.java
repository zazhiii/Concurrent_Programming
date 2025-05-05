package com.zazhi.P06;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * @author zazhi
 * @date 2025/5/5
 * @description:
 */
public class P02_AtomicInteger {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(1);

        System.out.println(i.incrementAndGet()); // ++ i; 2
        System.out.println(i.getAndIncrement()); // i ++; 2

        System.out.println(i.get()); // 3

        System.out.println(i.addAndGet(10)); // 3 --> 13
        System.out.println(i.getAndAdd(10)); // 13 --> 23

        System.out.println(i.updateAndGet(value -> value * 10)); // 23 --> 230

        System.out.println(i.getAndUpdate(value -> value * 10)); // 230 --> 2300

        System.out.println(i.get()); // 2300

        // =======================
        // 自己实现一个updateAndGet方法, 这里也用到了函数式编程的思想
        System.out.println(updateAndGet(i, value -> value * 10)); // 2300 --> 23000

    }

    public static int updateAndGet(AtomicInteger i, IntUnaryOperator updateFunction) {
        while(true){
            int current = i.get();
            int next = updateFunction.applyAsInt(current);
            if(i.compareAndSet(current, next)){
                return next;
            }
        }
    }
}

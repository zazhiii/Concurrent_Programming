package com.zazhi.P06;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author zazhi
 * @date 2025/5/6
 * @description: 原子累加器
 */
public class P07_LongAdder {

    public static void main(String[] args) {

        // 耗时: 1382ms
        f(
                AtomicLong::new,
                AtomicLong::incrementAndGet
        );

        // LongAdder, 耗时: 221ms,
        f(
                LongAdder::new,
                LongAdder::increment
        );

    }

    public static <T> void f(Supplier<T> adder, Consumer<T> action){
        T t = adder.get();

        List<Thread> ts = new ArrayList<>();

        for(int i = 0; i < 1000; i++){
            ts.add(new Thread(() -> {
                for(int j = 0; j < 100000; j++){
                    action.accept(t);
                }
            }));
        }

        long start = System.currentTimeMillis();

        ts.forEach(Thread::start);
        ts.forEach(t1 -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.currentTimeMillis();

        System.out.println("耗时: " + (end - start) + "ms");

    }
}

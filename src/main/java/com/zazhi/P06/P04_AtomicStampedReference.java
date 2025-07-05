package com.zazhi.P06;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;

import static com.zazhi.C04.park_unpark.P08_Park_Unpark.sleep;

/**
 * @author zazhi
 * @date 2025/5/5
 * @description: AtomicStampedReference
 */
@Slf4j
public class P04_AtomicStampedReference {
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);
    public static void main(String[] args) {

        String prev = ref.getReference();
        int stamp = ref.getStamp();

        other();

        sleep(1000);

        log.debug("版本号: {}", ref.getStamp());
        log.debug("A --> B: {}", ref.compareAndSet(prev, "B", stamp,stamp + 1));

    }

    public static void other(){
        new Thread(() -> {
            int stamp = ref.getStamp();
            String prev = ref.getReference();
            log.debug("版本号: {}", ref.getStamp());
            log.debug("A --> B: {}", ref.compareAndSet(prev, "B", stamp, stamp + 1));
        }, "t1").start();
        sleep(500);
        new Thread(() -> {
            int stamp = ref.getStamp();
            String prev = ref.getReference();
            log.debug("版本号: {}", ref.getStamp());
            log.debug("B --> A: {}", ref.compareAndSet(prev, "A", stamp, stamp + 1));
        }, "t2").start();

    }
}

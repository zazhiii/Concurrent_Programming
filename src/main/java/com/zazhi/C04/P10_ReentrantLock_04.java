package com.zazhi.C04;

import com.zazhi.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zazhi
 * @date 2025/4/29
 * @description: 哲学家就餐问题解决
 */
@Slf4j
public class P10_ReentrantLock_04 {

    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");
        new Philosopher("苏格拉底", c1, c2).start();
        new Philosopher("柏拉图", c2, c3).start();
        new Philosopher("亚里士多德", c3, c4).start();
        new Philosopher("赫拉克利特", c4, c5).start();
        new Philosopher("阿基米德", c5, c1).start();
    }
}

class ChopstickV2 extends ReentrantLock {
    String name;

    public ChopstickV2(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}

@Slf4j
class PhilosopherV2 extends Thread {
    ChopstickV2 left;
    ChopstickV2 right;

    public PhilosopherV2(String name, ChopstickV2 left, ChopstickV2 right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    private void eat() {
        log.debug("eating...");
        SleepUtil.sleep(1000);
    }

    @Override
    public void run() {
        while (true) {
            if(left.tryLock()){
                try{
                    if(right.tryLock()){
                        try{
                            eat();
                        }finally {
                            right.unlock();
                        }
                    }
                }finally {
                    left.unlock();
                }
            }
        }
    }
}

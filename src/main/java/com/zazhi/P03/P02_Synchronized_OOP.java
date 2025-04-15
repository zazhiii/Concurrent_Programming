package com.zazhi.P03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/15
 * @description: synchronized 关键字 面向对象改造
 */
@Slf4j
public class P02_Synchronized_OOP {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                room.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        log.debug("count: {}", room.getCount()); // count: 0
    }
}

class Room{
    private int count = 0;
    public void increment(){
        synchronized (this) {
            count++;
        }
    }
    public void decrement(){
        synchronized (this) {
            count--;
        }
    }
    public int getCount(){
        synchronized (this) {
            return count;
        }
    }
}


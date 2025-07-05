package com.zazhi.C04;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @author zazhi
 * @date 2025/4/17
 * @description: 线程安全问题 -- 卖票问题
 */
@Slf4j
public class P03_Thread_safety_issues {

    public static void main(String[] args) {
        TicketWindow ticketWindow = new TicketWindow(2000);
        List<Thread> list = new ArrayList<>();
        // 用来存储买出去多少张票
        List<Integer> sellCount = new Vector<>();
        for (int i = 0; i < 8000; i++) {
            Thread t = new Thread(() -> {
                // 分析这里的竞态条件
                int count = ticketWindow.sell(randomAmount());
                sellCount.add(count);
            });
            list.add(t);
            t.start();
        }
        list.forEach((t) -> {
            try {
                t.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.debug("卖票数量: {}",sellCount.stream().mapToInt(c -> c).sum());
        log.debug("剩余票数: {}", ticketWindow.getCount());
    }

    static Random random = new Random(); // Random 为线程安全
    public static int randomAmount() {
        return random.nextInt(5) + 1;
    }
}

class TicketWindow {
    private int count;
    public TicketWindow(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public synchronized int sell(int amount) { // 加锁
//    public int sell(int amount) {
        if (this.count >= amount) {

            try {
                Thread.sleep(1); // 模拟卖票的耗时, 模拟高并发情况下指令交错
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.count -= amount;
            return amount;
        }
        else {
            return 0;
        }
    }
}

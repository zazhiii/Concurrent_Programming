package com.zazhi.C04.wait_notify;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/26
 * @description: 设计模式 -- 保护性暂停
 *
 * 拓展: 超时等待
 *
 */
@Slf4j
public class P06_DesignPatterns_GuardedSuspension02 {
    // 模拟场景: 线程1等待线程2的下载结果

    public static void main(String[] args) {

        GuardedObjectV2 guardedObject = new GuardedObjectV2();

        new Thread(() -> {
            log.debug("线程1: 等待下载结果...");
            Object resp = guardedObject.get(1000);// 等待下载结果 超时等待
            log.debug("线程1: 下载结果: {}", resp);
        }, "t1").start();

        new Thread(() -> {
            try {
                log.debug("线程2: 开始下载...");
                Thread.sleep(2000); // 模拟下载时间
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log.debug("线程2: 下载完成, 通知线程1");
//            guardedObject.complete("下载完成"); // 通知线程1下载完成
            guardedObject.complete(null); // 模拟虚假唤醒
            log.debug("线程2: 继续执行其他任务...");
        },"t2").start();

    }
}

class GuardedObjectV2{
    private Object response;

    public Object get(long timeout){
        long start = System.currentTimeMillis();
        long passed = 0;
        while(response == null){
            if(passed > timeout){ // 超时
                break;
            }
            synchronized(this){
                try {
                    this.wait(timeout - passed); // 等待通知
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // 计算等待时间
            passed = System.currentTimeMillis() - start;
        }
        return response;
    }

    public void complete(Object response){
        synchronized(this){
            this.response = response;
            this.notifyAll(); // 通知等待的线程
        }
    }
}

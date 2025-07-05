package com.zazhi.C04.wait_notify;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/26
 * @description: 设计模式 -- 保护性暂停
 * GuardedSuspension: 一个线程等待另一个线程的通知
 *
 * 用GuardedSuspension模式与join()的好处
 * 1. 不用等待线程执行结束, 提交后就可以继续执行
 * 2. 用join()的等待结果变量只能设计成全局变量, 而GuardedSuspension可以设计成局部变量
 */
@Slf4j
public class P06_DesignPatterns_GuardedSuspension {
    // 模拟场景: 线程1等待线程2的下载结果

    public static void main(String[] args) {

        GuardedObject guardedObject = new GuardedObject();

        new Thread(() -> {
            log.debug("线程1: 等待下载结果...");
            Object resp = guardedObject.get();// 等待下载结果 超时等待
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

class GuardedObject{
    private Object response;

    public Object get(){
        while(response == null){
            synchronized(this){
                try {
                    this.wait(); // 等待通知
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
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

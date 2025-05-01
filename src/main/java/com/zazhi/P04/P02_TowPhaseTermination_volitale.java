package com.zazhi.P04;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/15
 * @description: 两阶段终止设计模式 -- 使用volatile关键字
 */
@Slf4j
public class P02_TowPhaseTermination_volitale {

    public static void main(String[] args) throws InterruptedException {
        P02_TowPhaseTermination_volitale termination = new P02_TowPhaseTermination_volitale();
        termination.start();
        Thread.sleep(2500);
        log.debug("准备停止监控线程");
        termination.stop();
    }


    private Thread monitor;

    private volatile boolean stop = false;

    /**
     * 启动监控线程
     */
    public void start(){
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                if(stop){
                    log.debug("线程被打断，料理后事");
                    break;
                }

                log.debug("监控线程正在运行");

                try {
                    Thread.sleep(1000); // 模拟监控间隔
                } catch (InterruptedException e) {

                }
            }
        });
        monitor.start();
    }

    public void stop(){
        stop = true; // 设置停止标志
        monitor.interrupt(); // 中断线程 保证在sleep时也打断
    }
}

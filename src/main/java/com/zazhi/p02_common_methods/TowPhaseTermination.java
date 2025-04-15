package com.zazhi.p02_common_methods;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/4/15
 * @description: 两阶段终止设计模式
 */
@Slf4j
public class TowPhaseTermination {

    public static void main(String[] args) throws InterruptedException {
        TowPhaseTermination termination = new TowPhaseTermination();
        termination.start();
        Thread.sleep(3000);
        log.debug("准备停止监控线程");
        termination.stop();
    }


    private Thread monitor;

    /**
     * 启动监控线程
     */
    public void start(){
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                boolean interrupted = currentThread.isInterrupted();// 清除中断标志
                if(interrupted){
                    log.debug("线程被打断，料理后事");
                    break;
                }

                log.debug("监控线程正在运行");

                try {
                    Thread.sleep(1000); // 模拟监控间隔
                } catch (InterruptedException e) {
                    log.debug("监控线程睡眠时被打断，重设打断标记为true");
                    currentThread.interrupt();
                }
            }
        });
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }
}

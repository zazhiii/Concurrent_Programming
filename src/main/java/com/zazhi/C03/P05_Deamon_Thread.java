package com.zazhi.C03;

/**
 * @author zazhi
 * @date 2025/7/5
 * @description: 守护线程
 */
public class P05_Deamon_Thread {
    public static void main(String[] args) {
        // 创建守护线程
        Thread daemonThread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("守护线程运行中...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 设置为守护线程（必须在start()前调用）
        daemonThread.setDaemon(true);
        daemonThread.start();

        // 主线程（用户线程）运行5秒后退出
        try {
            System.out.println("主线程开始运行...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程退出，JVM将终止所有守护线程");
        // 主线程退出后，守护线程会立即终止
    }
}

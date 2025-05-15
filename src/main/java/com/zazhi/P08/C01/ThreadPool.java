package com.zazhi.P08.C01;

import com.zazhi.P08.C01.reject_policy.RejectPolicy;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zazhi
 * @date 2025/5/15
 * @description: ThreadPool
 */
@Slf4j
public class ThreadPool {

    private BlockingQueue<Runnable> taskQueue;

    private Set<Worker> workers = new HashSet<>();

    private int coreSize;

    private long timeout;

    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int capcity, RejectPolicy<Runnable> rejectPolicy) {
        this.taskQueue = new BlockingQueue<>(capcity);
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task){
        synchronized (workers) {
            if(workers.size() < coreSize){
                Worker worker = new Worker(task);
                log.debug("新建线程: {}, {}", worker.getName(), task);
                workers.add(worker);
                worker.start();
            }else{
//                log.debug("线程池已满，加入任务队列: {}", task);
//                taskQueue.put(task);
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }

    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
//            while(task != null || (task = taskQueue.take()) != null){
            while(task != null || (task = taskQueue.poll(timeout, timeUnit)) != null){
                try {
                    log.debug("开始执行任务: {}", task);
                    task.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    task = null;
                }
            }

            // 如果线程关闭了，移除自己
            synchronized (workers) {
                log.debug("线程结束: {}", this.getName());
                workers.remove(this);
            }
        }
    }
}

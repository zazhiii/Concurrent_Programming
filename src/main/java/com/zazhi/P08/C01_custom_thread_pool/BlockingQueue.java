package com.zazhi.P08.C01_custom_thread_pool;

import com.zazhi.P08.C01_custom_thread_pool.reject_policy.RejectPolicy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zazhi
 * @date 2025/5/15
 * @description: BlockingQueue
 */
@Slf4j
public class BlockingQueue<T> {

    private Deque<T> queue = new ArrayDeque<>();

    private int capacity;

    private ReentrantLock lock = new ReentrantLock();

    private Condition fullWaitSet = lock.newCondition();
    private Condition emptyWaitSet = lock.newCondition();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public T poll(long timeout, TimeUnit timeUnit){
        lock.lock();
        try{
            long nanos = timeUnit.toNanos(timeout);
            while(queue.isEmpty()){
                if(nanos <= 0){
                    return null;
                }
                nanos = emptyWaitSet.awaitNanos(nanos);
            }
            T t = queue.pollFirst();
            fullWaitSet.signalAll();
            return t;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public T take(){
        lock.lock();
        try{
            while(queue.isEmpty()){
                emptyWaitSet.await();
            }
            T t = queue.pollFirst();
            fullWaitSet.signalAll();
            return t;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void put(T element){
        lock.lock();
        try{
            while(queue.size() == capacity){
                log.debug("队列已满，等待加入任务: {}", element);
                fullWaitSet.await();
            }
            log.debug("加入任务: {}", element);
            queue.addLast(element);
            emptyWaitSet.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(T task, long timeout, TimeUnit timeUnit){
        lock.lock();
        try{
            long nanos = timeUnit.toNanos(timeout);
            while(queue.size() == capacity){
                if(nanos <= 0){
                    return false;
                }
                nanos = fullWaitSet.awaitNanos(nanos);
            }
            queue.addLast(task);
            emptyWaitSet.signalAll();
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }


    public int size(){
        lock.lock();
        try{
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            if(queue.size() == capacity){
                rejectPolicy.reject(this, task);
            }else{
                log.debug("加入任务: {}", task);
                queue.addLast(task);
                emptyWaitSet.signalAll();
            }
        }finally {
            lock.unlock();
        }
    }
}

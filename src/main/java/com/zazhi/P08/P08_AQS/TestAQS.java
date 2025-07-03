package com.zazhi.P08.P08_AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.zazhi.util.SleepUtil.sleep;

public class TestAQS {

    public static volatile int I = 0;


    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new ReentrantLock();

        // 测试 1
        new Thread(() -> {
            lock.lock(); // 获取锁
            System.out.println(Thread.currentThread().getName() + "线程获取锁");
            try {
                sleep(1000);
            } finally {
                lock.unlock(); // 释放锁
                System.out.println(Thread.currentThread().getName() + "线程释放锁");
            }
        }).start();
        new Thread(() -> {
            lock.lock(); // 获取锁
            try {
                System.out.println(Thread.currentThread().getName() + "线程获取锁");
            } finally {
                lock.unlock(); // 释放锁
                System.out.println(Thread.currentThread().getName() + "线程释放锁");
            }
        }).start();

        // 测试 2
        new Thread(() -> {
            for(int i = 0; i < 100000; i ++){
                lock.lock(); // 获取锁
                try {
                    I ++;
                } finally {
                    lock.unlock(); // 释放锁
                }
            }
        }).start();
        new Thread(() -> {
            for(int i = 0; i < 100000; i ++){
                lock.lock(); // 获取锁
                try {
                    I --;
                } finally {
                    lock.unlock(); // 释放锁
                }
            }
        }).start();
        System.out.println("I的值: " + I); // 输出I的值
    }

}

class MyLock implements Lock {

    class MySync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override // 是否有独占锁
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final MySync sync = new MySync();

    @Override // 加锁 （不成功会进入等待队列）
    public void lock() {
        sync.acquire(1); // 尝试获取锁，参数1表示获取锁的数量
    }

    @Override // 可中断的加锁
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1); // 尝试获取锁，如果被中断则抛出InterruptedException
    }

    @Override // 尝试加锁，如果成功则返回true，否则返回false
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override // 尝试加锁，直到指定时间，如果成功则返回true，否则返回false
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override // 解锁
    public void unlock() {
        sync.release(1); // 释放锁，参数1表示释放锁的数量
    }

    @Override //创建条件变量
    public Condition newCondition() {
        return sync.newCondition(); // 返回一个新的条件变量
    }
}

# 并发编程
### 四、共享模型之管程

park-unpark 使用与原理

重新理解线程状态转换

多把锁（不同的功能用不同的锁）

活跃性
- 死锁
  - 死锁现象
  - 定位死锁（两种方法）
  - 哲学家就餐问题
- 活锁
- 饥饿

ReentrantLock
- 可重入（synchronized和ReentrantLock都是可重入锁）
- 可打断（`lockInterruptibly()`）
- 锁超时（`tryLock()`）
- 公平锁（`new ReentrantLock(true)`）
- 条件变量（`Condition`——不同的休息室）

### 五、共享模型之内存
1. JMM
   - 原子性
   - 可见性(volatile 关键字)  
   - 有序性


# 设计模式
1. GuardedSuspension 保护性暂停
2. 生产者消费者
3. 顺序控制
   - 固定顺序
   - 交替运行
---
1. 两阶段终止 -- volatile
2. balking模式 (犹豫模式, 一件事情只做一次)

# 原理
park-unpark

# 参考资料
https://www.bilibili.com/video/BV16J411h7Rd?p=108&vd_source=7ec6c2c9c716c60b7a5c2e5951d2b81b

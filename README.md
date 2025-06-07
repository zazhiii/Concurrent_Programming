# 并发编程
## 四、共享模型之管程

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

## 五、共享模型之内存
1. JMM
   - 原子性
   - 可见性(volatile 关键字) 
     - happens-befores规则
   - 有序性(指令重排；禁用指令重排：volatile)

## 六、共享模型之无锁

1. 从取款案例和`AtomicInteger`类理解CAS

2. CAS的特点
   - 为什么CAS效率高

3. `AtomicInteger`

4. `AtomicReference`（原子引用）

5. `AtomicStampedReference`（原子戳引用, 通过版本号感知修改）

6. `AtomicMarkableReference`（原子标记引用, 通过布尔值感知修改）

7. `AtomicIntegerArray`（原子整形数组, 保护数组中的元素）

8. `LongAdder`（性能更高）
   - 原理
   - 源码

## 八、共享模型之工具
1. 手写线程池
2. `ThreadPoolExecutor`
   1. 线程池的状态
   2. 构造方法（重要）
   3. `Executors`类
   4. `submit()`方法
   5. `invokeAll()`方法
   6. `invokeAny()`方法
   7. `shutdown()`方法 -- 不会停止已经提交的任务
   8. `shutdownNow()`方法 -- 会停止已经提交的任务
3. 工作线程设计模式（Worker Thread）-- 典型实现为线程池
   1. 饥饿的解决方式：不同的任务使用不同的线程池
   2. 池大小
      1. CPU 密集型任务：CPU 核心数 + 1
      2. IO 密集型任务：CPU 核心数 * 2
4. `Timer`类（不推荐使用）
5. `ScheduledThreadPoolExecutor`
   - 定时执行任务: `schedule()`
   - 周期执行任务: `scheduleAtFixedRate()`, `scheduleWithFixedDelay()` 注意二者的区别
6. 处理线程池中的异常
   1. 在任务中 try-catch
   2. 使用`Future`的`get()`方法, 异常会封装到其中
7. `Fork/Join`线程池
# 设计模式
1. GuardedSuspension 保护性暂停
2. 生产者消费者
3. 顺序控制
   - 固定顺序
   - 交替运行
---
第五章
1. 两阶段终止 -- volatile实现（volatile修饰打断标记）
2. **balking模式** (犹豫模式, 一件事情只做一次)



# 原理
park-unpark

- volatile 原理
  - 保证可见性（读屏障、写屏障）
  - 保证有序性 

# 参考资料
https://www.bilibili.com/video/BV16J411h7Rd?p=108&vd_source=7ec6c2c9c716c60b7a5c2e5951d2b81b

package com.zazhi.P08.C01_custom_thread_pool.reject_policy;

import com.zazhi.P08.C01_custom_thread_pool.BlockingQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zazhi
 * @date 2025/5/15
 * @description: TODO
 */
@Slf4j
public class WaitTimeoutRejectPolicy<T> implements RejectPolicy<T>{

    private long timeout;

    private TimeUnit unit;

    public WaitTimeoutRejectPolicy(long timeout, TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
    }

    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        boolean offered = queue.offer(task, timeout, unit);
        log.debug(offered ? "任务加入队列: {}" : "任务加入队列失败: {}", task);
    }
}

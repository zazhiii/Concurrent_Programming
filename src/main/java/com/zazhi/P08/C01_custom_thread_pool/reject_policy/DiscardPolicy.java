package com.zazhi.P08.C01_custom_thread_pool.reject_policy;

import com.zazhi.P08.C01_custom_thread_pool.BlockingQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zazhi
 * @date 2025/5/15
 * @description: TODO
 */
@Slf4j
public class DiscardPolicy<T> implements RejectPolicy<T> {

    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        // do nothing
        log.debug("任务被丢弃: {}", task);
    }
}

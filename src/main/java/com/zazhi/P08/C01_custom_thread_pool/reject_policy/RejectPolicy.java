package com.zazhi.P08.C01_custom_thread_pool.reject_policy;

import com.zazhi.P08.C01_custom_thread_pool.BlockingQueue;

@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}

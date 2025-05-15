package com.zazhi.P08.C01.reject_policy;

import com.zazhi.P08.C01.BlockingQueue;

@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}

package com.zazhi.P08.C01.reject_policy;

import com.zazhi.P08.C01.BlockingQueue;

/**
 * @author zazhi
 * @date 2025/5/15
 * @description:
 */
public class AbortPolicy<T> implements RejectPolicy<T> {

    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        throw new RuntimeException("任务被拒绝: " + task);
    }

}

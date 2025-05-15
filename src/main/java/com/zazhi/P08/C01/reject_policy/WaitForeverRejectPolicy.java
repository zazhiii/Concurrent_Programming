package com.zazhi.P08.C01.reject_policy;

import com.zazhi.P08.C01.BlockingQueue;

/**
 * @author zazhi
 * @date 2025/5/15
 * @description: TODO
 */
public class WaitForeverRejectPolicy<T> implements RejectPolicy<T> {

    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        queue.put(task);
    }
}

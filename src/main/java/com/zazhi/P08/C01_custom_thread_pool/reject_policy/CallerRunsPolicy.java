package com.zazhi.P08.C01_custom_thread_pool.reject_policy;

import com.zazhi.P08.C01_custom_thread_pool.BlockingQueue;

/**
 * @author zazhi
 * @date 2025/5/15
 * @description: TODO
 */
public class CallerRunsPolicy<T> implements RejectPolicy<T> {

    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        // 直接在调用线程中执行任务
        ((Runnable)task).run();
    }
}

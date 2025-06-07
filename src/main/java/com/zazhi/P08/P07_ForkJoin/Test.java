package com.zazhi.P08.P07_ForkJoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class Test {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);

        int res = pool.invoke(new MyTask(10)); // 计算1+2+3+...+10

        log.info("计算结果: {}", res); // 输出结果: 55

    }
}

class MyTask extends RecursiveTask<Integer>{

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }
        MyTask task1 = new MyTask(n - 1);
        task1.fork(); // 异步执行
        return n + task1.join(); // 等待结果并返回
    }
}

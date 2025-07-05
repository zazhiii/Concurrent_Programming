package com.zazhi.C04;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author zazhi
 * @date 2025/4/26
 * @description: 设计模式 -- 生产者消费者
 */
public class P07_DesignPatterns_ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {

        MessegeQueue queue = new MessegeQueue(2);

        for (int i = 0; i < 3; i++) {
            int I = i;
            new Thread(() -> {
                queue.put(new Message(1, "消息" + I));
            }, "生产者" + i).start();
        }

        Thread.sleep(1000);

        new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                queue.take();
            }
        }, "消费者").start();
    }
}

@Slf4j
// 消息队列 -- 实现线程之间的通信 (rabbitMQ那些实现了进程之间的通信)
class MessegeQueue {
    private Deque<Message> queue = new ArrayDeque<>();

    private int capacity;

    public MessegeQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    log.debug("队列为空, 等待生产...");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message msg = queue.removeFirst();
            log.debug("消费消息: {}", msg);
            queue.notifyAll();
            return msg;
        }
    }

    public void put(Message message) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                try {
                    log.debug("队列已满, 等待消费...");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(message);
            log.debug("生产消息: {}", message);
            queue.notifyAll();
        }
    }
}

final class Message {
    public Message(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    private Integer id;
    private String content;
}
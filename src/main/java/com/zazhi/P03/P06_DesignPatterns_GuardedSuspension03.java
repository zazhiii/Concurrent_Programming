package com.zazhi.P03;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zazhi
 * @date 2025/4/26
 * @description: 设计模式 -- 保护性暂停
 */
@Slf4j
public class P06_DesignPatterns_GuardedSuspension03 {
    // 模拟场景: 线程1等待线程2的下载结果

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 3; i++){
            new People().start();
        }

        Thread.sleep(1000);

        for(int i : MailBox.getIds()){
            new Postman(i, "邮件" + i).start();
        }
    }
}

@Slf4j
class People extends Thread{
    @Override
    public void run() {
        GuardedObjectV3 guardedObjectV3 = MailBox.putMail();
        log.debug("开始收信 id:{}", guardedObjectV3.getId());
        Object mail = guardedObjectV3.get(5000);
        log.debug("收到信 id:{}, mail:{}", guardedObjectV3.getId(), mail);
    }
}

@Slf4j
class Postman extends Thread{

    private Integer id;
    private String mail;

    public Postman(Integer id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObjectV3 guardedObjectV3 = MailBox.getMail(id);
        log.debug("开始投递 id:{}, mail:{}", id, mail);
        guardedObjectV3.complete(mail);
    }
}

class MailBox{
    private static Map<Integer, GuardedObjectV3> box = new ConcurrentHashMap<>();
    private static int id = 0;
    public static synchronized int genId(){
        return id++;
    }
    public static GuardedObjectV3 getMail(Integer id){
        return box.remove(id);
    }
    public static GuardedObjectV3 putMail(){
        GuardedObjectV3 go = new GuardedObjectV3(genId());
        box.put(go.getId(), go);
        return go;
    }
    public static Set<Integer> getIds(){
        return box.keySet();
    }
}

@Data
class GuardedObjectV3{

    private Integer id; // 唯一标识

    private Object response;

    public GuardedObjectV3(Integer id) {
        this.id = id;
    }

    public Object get(long timeout){
        long start = System.currentTimeMillis();
        long passed = 0;
        while(response == null){
            if(passed > timeout){ // 超时
                break;
            }
            synchronized(this){
                try {
                    this.wait(timeout - passed); // 等待通知
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // 计算等待时间
            passed = System.currentTimeMillis() - start;
        }
        return response;
    }

    public void complete(Object response){
        synchronized(this){
            this.response = response;
            this.notifyAll(); // 通知等待的线程
        }
    }
}

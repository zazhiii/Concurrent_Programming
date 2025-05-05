package com.zazhi.P06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zazhi
 * @date 2025/5/5
 * @description: cas
 */
public class P01_CAS {

    public static void main(String[] args) {
        Account account1 = new AccountAtomic(10000);

        List<Thread> threads1 = new ArrayList<>();

        for(int i = 1; i <= 1000; i ++){
            threads1.add(new Thread(() -> {
                account1.withdraw(10);
            }));
        }

        threads1.forEach(Thread::start);

        threads1.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Account 1 balance: " + account1.getBalance());

        // 使用CAS操作进行取款 ===================
        Account account2 = new AccountUnsafe(10000);

        List<Thread> threads2 = new ArrayList<>();

        for(int i = 1; i <= 1000; i ++){
            threads2.add(new Thread(() -> {
                account2.withdraw(10);
            }));
        }

        threads2.forEach(Thread::start);

        threads2.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Account 2 balance: " + account2.getBalance());
    }
}

class AccountAtomic implements Account {

    private AtomicInteger balance;

    public AccountAtomic(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public int getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(int amount) {
        // 使用CAS操作进行取款
        while (true) {
            int currentBalance = balance.get();
            // 尝试更新余额
            if (balance.compareAndSet(currentBalance, currentBalance - amount)) {
                return;
            }
        }
    }
}

class AccountUnsafe implements Account {
    private Integer balance;

    public AccountUnsafe(int balance) {
        this.balance = balance;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void withdraw(int amount) {
        balance -= amount;
    }
}

interface Account {
    // 获取余额
    int getBalance();

    // 取款
    void withdraw(int amount);
}
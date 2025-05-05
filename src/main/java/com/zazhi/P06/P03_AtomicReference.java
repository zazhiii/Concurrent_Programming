package com.zazhi.P06;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zazhi
 * @date 2025/5/5
 * @description: AtomicReference
 */
public class P03_AtomicReference {

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        DecimalAccount account = new DecimalAccountCAS(new BigDecimal("10000"));

        for (int i = 1; i <= 1000; i++) {
            threads.add(new Thread(() -> {
                account.withdraw(new BigDecimal("10"));
            }));
        }

        threads.forEach(Thread::start);

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Account balance: " + account.getBalance());
    }

}

class DecimalAccountCAS implements DecimalAccount {

    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCAS(BigDecimal initialBalance) {
        this.balance = new AtomicReference<>(initialBalance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal currentBalance = balance.get();
            BigDecimal newBalance = currentBalance.subtract(amount);
            if (balance.compareAndSet(currentBalance, newBalance)) {
                break;
            }
        }
    }
}


interface DecimalAccount {

    void withdraw(BigDecimal amount);

    BigDecimal getBalance();
}

package com.zazhi.P06;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author zazhi
 * @date 2025/5/5
 * @description: AtomicReferenceFieldUpdater
 */

public class P06_AtomicReferenceFieldUpdater {

    public static void main(String[] args) {
        Student student = new Student();
        AtomicReferenceFieldUpdater<Student, String> updater = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        String prev = updater.get(student);// null

        //模拟并发修改
//        student.name = "zazhi"; // --> CAS失败

        System.out.println(updater.compareAndSet(student, prev, "zazhi")); // true
    }
}

class Student{
    volatile String name;
}
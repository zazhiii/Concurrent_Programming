package com.zazhi.C03;

/**
 * @author zazhi
 * @date 2025/7/3
 * @description: 测试栈帧
 */
public class P04_stack_frame {

    public static void main(String[] args) {
        f1(10);
    }

    public static void f1(int x){
        int y = x + 1;
        Object o = f2();
        System.out.println();
    }

    private static Object f2() {
        Object o = new Object();
        return o;
    }

}

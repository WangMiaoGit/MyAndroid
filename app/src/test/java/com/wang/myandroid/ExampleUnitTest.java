package com.wang.myandroid;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void T() {
//        int a = 1,
//                b = 2;
//        System.out.println(a + b);   //3  加
//        System.out.println(a - b);   //-1 减
//        System.out.println(a * b);   //2  乘
//        System.out.println(a / b);   //0  除  java因为类型转化问题  丢失精度
//        System.out.println(a % b);   //1  取模
//
//        int c = 1;
//        double d = 2.0;
//        System.out.println(c + d);   //3.0  加
//        System.out.println(c - d);   //-1.0 减
//        System.out.println(c * d);   //2.0  乘
//        System.out.println(c / d);   //0.5  除  java因为类型转化问题  丢失精度
//        System.out.println(c % d);   //1.0  取模


//        int a = 1;
//        double b = 1.0;
//        System.out.println(a == b);  //只比较数值结果

        new Thread(()-> System.out.println("111")).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("111");
            }
        }).start();

        Runnable runnable = () -> System.out.println("1");
    }
}
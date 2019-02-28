package com.wang.myandroid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        String s = "学java";
        System.out.println(s.getBytes("GBK").length);
        Class<?> ccc = Class.forName("ccc");
        ccc.getClass().getName();
        ccc.getFields();



        List list = new ArrayList(2);
    }

    public abstract class aaa {
        abstract void aaa();

        public abstract void aaa1();

        protected abstract void aaa2();

    }

    public class CCC {
        int i = 1;
        String a = "123";

        boolean i(int i, String s) {

            return i == Integer.parseInt(s);
        }
    }

    public interface bbb {
        void bbb();

        public void bbb1();

        public abstract void bbb2();
    }

    //选择排序
    public void sort_select(int[] nums) {
        int N = nums.length;
        for (int i = 0; i < N - 1; i++) {
            int min = i;//最小元素角标
            for (int j = i + 1; j < N; j++) {
                if (nums[j] < nums[min]) {
                    min = j;//内层循环找到最小的角标
                }
            }
            //在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换
            if (i != min) {  //交换a[i]和a[k]
                int temp = nums[i];
                nums[i] = nums[min];
                nums[min] = temp;
            }
        }
    }

    //冒泡排序   第一轮循环没有操作时  可判断是已排序
    public void sort_bubble(int[] nums) {
        int N = nums.length;
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < N - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[i] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

    //插入排序
    public void sort_insert(int[] nums) {
        int len = nums.length;//单独把数组长度拿出来，提高效率
        int insertNum;//要插入的数
        for (int i = 1; i < len; i++) {//因为第一次不用，所以从1开始
            insertNum = nums[i];
            int j = i - 1;//序列元素个数
            while (j >= 0 && nums[j] > insertNum) {//从后往前循环，将大于insertNum的数向后移动
                nums[j + 1] = nums[j];//元素向后移动
                j--;
            }
            nums[j + 1] = insertNum;//找到位置，插入当前元素
        }

//        for (int i = 1; i < len; i++) {
//            for (int j = i; j > 0 && nums[j]<nums[j - 1]; j--) {
//                int temp = nums[j];
//                nums[i] = nums[j - 1];
//                nums[j - 1] = temp;
//            }
//        }
    }

    //希尔排序
    public void sort_shell(int[] nums) {
        int len = nums.length;//单独把数组长度拿出来，提高效率
        while (len != 0) {
            len = len / 2;
            for (int i = 0; i < len; i++) {//分组
                for (int j = i + len; j < nums.length; j += len) {//元素从第二个开始
                    int k = j - len;//k为有序序列最后一位的位数
                    int temp = nums[j];//要插入的元素
//                    for (; k >= 0 && temp < a[k]; k -= len) {
//                        a[k + len] = a[k];
//                    }
                    while (k >= 0 && temp < nums[k]) {//从后往前遍历
                        nums[k + len] = nums[k];
                        k -= len;//向后移动len位
                    }
                    nums[k + len] = temp;
                }
            }
        }
    }

    //归并排序
    public void sort_merge(int[] nums, int left, int right) {
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(nums, i, i + (s - 1), i + (t - 1));
                i += t;
            }
            if (i + (s - 1) < right)
                merge(nums, i, i + (s - 1), right);
        }
    }

    private static void merge(int[] data, int p, int q, int r) {
        int[] B = new int[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (data[s] <= data[t]) {
                B[k] = data[s];
                s++;
            } else {
                B[k] = data[t];
                t++;
            }
            k++;
        }
        if (s == q + 1)
            B[k++] = data[t++];
        else
            B[k++] = data[s++];
        for (int i = p; i <= r; i++)
            data[i] = B[i];
    }

    //快速排序
    public void sort_quick(int[] nums, int low, int high) {
        //当low>high 直接跳出
        if (low >= high) {
            System.out.println(Arrays.toString(nums));
            return;
        }
        int start = low;
        int end = high;
        int key = nums[low];


        while (end > start) {
            //从后往前比较
            while (end > start && nums[end] >= key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            if (nums[end] <= key) {
                int temp = nums[end];
                nums[end] = nums[start];
                nums[start] = temp;
            }
            //从前往后比较
            while (end > start && nums[start] <= key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            if (nums[start] >= key) {
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归  第二大数  判断标定点 是不是第二大，不是就在右边子集递归
//        if (start<nums.length-2){
//
//        }
        if (start > low) sort_quick(nums, low, start - 1);//左边序列。第一个索引位置到关键值索引-1
        if (end < high) sort_quick(nums, end + 1, high);//右边序列。从关键值索引+1到最后一个
    }
}
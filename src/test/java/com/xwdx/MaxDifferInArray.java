package com.xwdx;

import java.util.Random;

/**
 * @author: kongmingliang
 * @create: 2022-04-03 09:47:20
 */
public class MaxDifferInArray {

    /**
     * 随机给一个无序数组，从前到后取两个值，求最大的增量值(数组后面的数减前面的数)
     *
     * 例如数组为  {7, 8, 33, 1, 12, 29, 9, 3, 6}
     * 则取1和29，最大增量值为 28
     * @param args
     */
    public static void main(String[] args) {
        //int arr[] = {7, 8, 33, 1, 12, 29, 9, 3, 6};
        int arr[] = generateArr(50, 50);

        for (int i = 0; i < 50000000; i++) {
            int r1 = getMaxDifferInOnceLoop(arr);
            int r2 = getMaxDiffer(arr);
            if (r1 != r2){
                System.out.println(arr);
            }
        }


    }

    // 一次循环搞定 O(n)
    public static int getMaxDifferInOnceLoop(int[] arr) {
        int result = 0; // 输出结果
        int curMax;  // 当前最大值
        int start = 0; // 开始索引， 当前记录的起始最小值

        for (int i = 1; i < arr.length; i++) {
            int curPrice = arr[i];
            if (curPrice > arr[start]) {
                // 如果当前值大于start， 当前最大增量值为当前值 - 开始值
                curMax = curPrice - arr[start];
                if (result < curMax) {
                // 如果当前算出来的值比result大，则result记为当前最大值
                    result = curMax;
                }
            } else {
                // 如果当前值小于start，则直接把start记为当前下标
                start = i;
            }
        }

        return result;
    }

    // 双层循环 O(N^2)
    public static int getMaxDiffer(int[] arr) {
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            int start = arr[i];
            int max = 0;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] > max) {
                    max = arr[j];
                }
            }
            if ((max - start) > result) {
                result = max - start;
            }
        }
        return result;

    }


    public static int[] generateArr(int maxValue, int maxArrayLength) {
        Random random = new Random(47);
        int len = random.nextInt(maxArrayLength);
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = random.nextInt(maxValue);
        }

        return arr;
    }

}

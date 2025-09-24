package com.algovault.algo.sorting;
/************************************************************
 * 🔰 CLASS: RadixSort
 * ----------------------------------------------------------
 * 📌 Description  : Implementation of RadixSort
 * 🛠️  Purpose     : Sort integers efficiently when values can
 *                   be represented digit by digit.
 * 🧠 Complexity   : O(n * k), where k = numbers of digits
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 11-Aug-2025
 ************************************************************/

public class RadixSort {

    static void countingSortByDigit(int arr[], int n, int exp) {

        int[] output = new int[n];
        int[] count = new int[10];

        // count frequency of digits
        for(int i=0;i<n;i++) {
            int digit = (arr[i]/exp)%10;
            count[digit]++;
        }

        // cumulative  count
        for(int i=1; i<10; i++) {
            count[i] += count[i-1];
        }

        for(int i=n-1; i>=0; i--) {
            int digit = (arr[i]/exp) % 10;
            output[count[digit]-1] = arr[i];
            count[digit]--;
        }

        for(int i=0; i<n;i++) {
            arr[i] = output[i];
        }

    }

    static void radixSort(int[] arr, int n) {
        int max = arr[0];
        for(int i=1; i<n; i++) {
            if(arr[i] > max) {
                max = arr[i];
            }
        }

        // couting sort for each digit
        for(int exp=1; max/exp > 0; exp *=10) {
            countingSortByDigit(arr, n, exp);
        }
    }

    public static void main(String[] args) {

        int[] arr = {5, 4, 3, 2, 1};

        System.out.print("Before RadixSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        radixSort(arr, arr.length);

        System.out.print("\nAfter RadixSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

    }
}
/************************************************************
 * 🔚 End of RadixSort.java
 * 📣 Built for mastering radix sort operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

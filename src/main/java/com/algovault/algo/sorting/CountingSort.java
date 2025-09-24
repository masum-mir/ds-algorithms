package com.algovault.algo.sorting;
/************************************************************
 * 🔰 CLASS: CountingSort
 * ----------------------------------------------------------
 * 📌 Description  : Implementation of CountingSort
 * 🛠️  Purpose     : Sort integers efficiently when the range
 *                   of input values is not very large.
 * 🧠 Complexity   : O(n + k), where k = range of numbers
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 11-Aug-2025
 ************************************************************/

public class CountingSort {

    static void countingSort(int arr[], int n) {
        int max=0;
        for(int i=0; i<n;i++) {
            if(arr[i] > max) {
                max = arr[i];
            }
        }

        int[] count = new int[max+1];
        for(int i=0; i<n;i++) {
            count[arr[i]]++;
        }

        for(int i=1; i<=max; i++) {
            count[i] += count[i-1];
        }

        int[] output = new int[n];
        for(int i=n-1; i>=0; i--) {
            output[count[arr[i]]-1] = arr[i];
            count[arr[i]]--;
        }

        for(int i=0; i<n; i++) {
            arr[i] = output[i];
        }


    }

    public static void main(String[] args) {

        int[] arr = {2,5,3,0,2,3,0,3};

        System.out.print("Before CountingSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        countingSort(arr, arr.length);

        System.out.print("\nAfter CountingSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

    }
}
/************************************************************
 * 🔚 End of CountingSort.java
 * 📣 Built for mastering counting sort operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

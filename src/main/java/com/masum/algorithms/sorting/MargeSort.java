package com.masum.algorithms.sorting;
/************************************************************
 * 🔰 CLASS: MargeSort
 * ----------------------------------------------------------
 * 📌 Description : Implementation of MargeSort
 * 🛠️  Purpose     : It works by recursively dividing the input
 *                  array into two halves, recursively sorting the
 *                  two halves and finally merging them back together
 *                  to obtain the sorted array.
 *                  Divide:
 *                  1. [38, 27, 43, 10]  is divided into  [38, 27  ] and  [43, 10]
 *                  2. [38, 27]  is divided into  [38]  and  [27]
 *                  3. [43, 10]  is divided into  [43]  and  [10]
 *                  Conquer:
 *                  1. [38]  is already sorted. 2. [27]  is already sorted. 3. [43]  is already sorted. 4. [10]  is already sorted.
 *                  Merge:
 *                  1. Merge  [38]  and  [27]  to get  [27, 38]
 *                  2. Merge  [43]  and  [10]  to get  [10,43]
 *                  3. Merge  [27, 38]  and  [10,43]  to get the final sorted list  [10, 27, 38, 43]
 *
 * 🧠 Complexity   : O(n log n)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 10-Aug-2025
 ************************************************************/

public class MargeSort {

    static void mergeSort(int[] arr, int[] temp, int left, int right) {
        if(left<right) {
            int mid = (right+left)/2;  // middle point

            mergeSort(arr, temp, left, mid); // sort left half
            mergeSort(arr, temp,mid + 1, right); // sort right half

            int i=left, j = mid + 1;
            for(int k=left; k<=right; k++) {
                if(i>mid) {
                    temp[k] = arr[j++]; // left half
                } else if(j>right) {
                    temp[k] = arr[i++]; // right half
                } else if(arr[i]<arr[j]) {
                    temp[k] = arr[i++]; // take from left
                } else {
                    temp[k] = arr[j++]; // take from right
                }
            }

            // copy back to orginal array
            for(int k=left; k<=right; k++) {
                arr[k] = temp[k];
            }
        }
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        int[] arr = {5, 4, 3, 2, 1};

        System.out.print("Before MergeSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        int n = arr.length;
        int [] temp = new int[n];
        mergeSort(arr, temp, 0, n-1);
        System.out.print("\nAfter MergeSort: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }

    }
}
/************************************************************
 * 🔚 End of MargeSort.java
 * 📣 Built for mastering marge sort operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/
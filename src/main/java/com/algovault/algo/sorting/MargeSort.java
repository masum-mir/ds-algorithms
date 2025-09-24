package com.algovault.algo.sorting;
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
    static void merge(int[] arr, int left, int mid, int right) {

        // size
        int size1 = mid-left+1;
        int size2 = right-mid;

        // create arrays
        int[] leftArray = new int[size1];
        int[] rightArray = new int[size2];

        for(int i=0; i<size1; i++) {
            leftArray[i] = arr[left+i];
        }
        for(int j=0; j<size2; j++) {
            rightArray[j] = arr[mid+1+j];
        }

        int i=0, j=0;
        int k=left;

        while (i<size1 && j<size2) {
            if(leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i<size1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }
        while (j<size2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
    static void mergeSort(int[] arr, int left, int right) {
        if(left<right) {
            int mid = left+(right-left)/2;  // middle point

            //sort first and second halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            // merge sorted halves
            merge(arr, left, mid, right);
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
        mergeSort(arr, 0, n-1);
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
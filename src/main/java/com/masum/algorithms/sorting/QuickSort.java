package com.masum.algorithms.sorting;

/************************************************************
 * 🔰 CLASS: QuickSort
 * ----------------------------------------------------------
 * 📌 Description : Implementation of QuickSort
 * 🛠️  Purpose     : This class exists to quickly and efficiently
 *                   sort arrays by repeatedly partitioning them around
 *                   a pivot and sorting the partitions, a method that's
 *                   faster than simple algorithms like bubble sort or
 *                   insertion sort for large datasets.
 * 🧠 Complexity   : O(n²)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 10-Aug-2025
 ************************************************************/

public class QuickSort {
    static void quickSort(int arr[], int low, int high) {
        if(low<high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi-1); // sort left sub-array
            quickSort(arr, pi+1, high); // sort right sub-array
        }
    }
    static int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = low-1;

        for(int j=low; j<=high-1;j++) {
            if(arr[j]<pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+1, high);
        return i+1;
    }

    static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        int[] arr = {5, 4, 3, 2, 1};
        System.out.print("Before QuickSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        quickSort(arr, 0, arr.length - 1);
        System.out.print("\nAfter QuickSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

    }
}
/************************************************************
 * 🔚 End of QuickSort.java
 * 📣 Built for mastering quick sort operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

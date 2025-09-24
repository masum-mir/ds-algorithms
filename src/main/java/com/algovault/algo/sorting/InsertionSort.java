package com.algovault.algo.sorting;
/************************************************************
 * 🔰 CLASS: InsertionSort
 * ----------------------------------------------------------
 * 📌 Description : Implementation of InsertionSort
 * 🛠️  Purpose     : This class exists to arrange elements in ascending
 *                    order by repeatedly inserting each element
 *                    into its correct position.
 * 🧠 Complexity   : O(n²)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 05-Aug-2025
 ************************************************************/

public class InsertionSort {

    static void insertionSort(int arr[], int n) {
        int key, i;
        for(int j=1; j<n; j++) {
            key = arr[j];
            i=j-1;
            while (i>=0 && arr[i] > key) {
                arr[i+1] = arr[i];
                i = i-1;
            }
            arr[i+1] = key;
        }

    }

    public static void main(String[] args) {

        int[] arr = {5, 4, 3, 2, 1};

        System.out.print("Before InsertionSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        insertionSort(arr, arr.length);

        System.out.print("\nAfter InsertionSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

    }
}
/************************************************************
 * 🔚 End of InsertionSort.java
 * 📣 Built for mastering insertion sort operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

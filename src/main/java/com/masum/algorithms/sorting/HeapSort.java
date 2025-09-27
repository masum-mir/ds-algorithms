package com.masum.algorithms.sorting;
/************************************************************
 * 🔰 CLASS: HeapSort
 * ----------------------------------------------------------
 * 📌 Description : Implementation of HeapSort
 * 🛠️  Purpose     : To sort an array using Heap Data Structure
 * 🧠 Complexity   : O(n log n)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 11-Aug-2025
 ************************************************************/

public class HeapSort {

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2*i+1;
        int right = 2*i+2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            heapify(arr, n, largest);
        }
    }

    static void heapSort(int arr[], int n) {

        // build max heap
        for(int i=n/2-1; i>=0; i--) {
            heapify(arr,n, i);
        }

        // extract elements one by one
        for (int i = n - 1; i > 0; i--) {

            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    public static void main(String[] args) {

        int[] arr = {5, 4, 3, 2, 1};

        System.out.print("Before HeapSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        heapSort(arr, arr.length);

        System.out.print("\nAfter HeapSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

    }
}
/************************************************************
 * 🔚 End of HeapSort.java
 * 📣 Built for mastering heap sort operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

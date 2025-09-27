package com.masum.algorithms.sorting;
/************************************************************
 * 🔰 CLASS: BubbleSort
 * ----------------------------------------------------------
 * 📌 Description : Implementation of BubbleSort
 * 🛠️  Purpose     : This class exists to arrange elements in ascending
 *                   order by repeatedly swapping adjacent elements
 *                   that are out of order.
 * 🧠 Complexity   : O(n²)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 05-Aug-2025
 ************************************************************/

public class BubbleSort {
    static void bubbleSort(int arr[], int n) {

        for(int i=0;i<n-1;i++) {
            for(int j=0;j<n-1; j++) {
                if(arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        int[] arr = {5, 4, 3, 2, 1};

        System.out.print("Before BubbleSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        bubbleSort(arr, arr.length);

        System.out.print("\nAfter BubbleSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
/************************************************************
 * 🔚 End of BubbleSort.java
 * 📣 Built for mastering bubble sort operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

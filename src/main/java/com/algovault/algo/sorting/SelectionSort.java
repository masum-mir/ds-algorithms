package com.algovault.algo.sorting;
/************************************************************
 * 🔰 CLASS: SelectionSort
 * ----------------------------------------------------------
 * 📌 Description : Implementation of SelectionSort
 * 🛠️  Purpose     : This class exists to arrange elements in ascending
 *                    order by repeatedly inserting each element
 *                    into its correct position.
 * 🧠 Complexity   : O(n²)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 09-Aug-2025
 ************************************************************/

public class SelectionSort {

    static void selectionSort(int arr[], int n) {
        int i, min = 0, j;
       for(i=0;i<n-1; i++) {
           min = i;

           for(j=i+1; j<n; j++) {
                if(arr[j] < arr[min]) {
                    min = j;
                }
           }
           //swap minimum element
           int temp = arr[i];
           arr[i] = arr[min];
           arr[min] = temp;

       }
    }

    public static void main(String[] args) {

        int[] arr = {5, 4, 3, 2, 1};

        System.out.print("Before SelectionSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        selectionSort(arr, arr.length);

        System.out.print("\nAfter SelectionSort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

    }
}
/************************************************************
 * 🔚 End of SelectionSort.java
 * 📣 Built for mastering selection  sort operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

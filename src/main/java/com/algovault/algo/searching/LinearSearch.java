package com.algovault.algo.searching;
/************************************************************
 * 🔰 CLASS: LinearSearch
 * ----------------------------------------------------------
 * 📌 Description : Implementation of Linear Search algorithm
 * 🛠️  Purpose    : Find an element’s index in an array
 * 🧠 Complexity  : O(n)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 05-Aug-2025
 ************************************************************/

public class LinearSearch {

    // iterative binary search
    static int linearSearch(int arr[], int data){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == data) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        int arr[] = {2, 4, 6, 8, 10, 12, 14};
        int target = 20;

        int iterativeResult = linearSearch(arr, target);
        System.out.println("Data is"+(iterativeResult!=-1?" Found at index: "+iterativeResult:" Not found."));

    }

}
/************************************************************
 * 🔚 End of LinearSearch.java
 * 📣 Built for mastering basic searching algorithm
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

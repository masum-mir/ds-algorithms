package com.masum.algorithms.searching;
/************************************************************
 * 🔰 CLASS: BinarySearch
 * ----------------------------------------------------------
 * 📌 Description : Implementation of Binary Search algorithm
 * 🛠️  Purpose    : Efficiently find an element’s index in
 *                   a sorted array
 * 🧠 Complexity   : O(log n)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 05-Aug-2025
 ************************************************************/

public class BinarySearch {

    // iterative binary search
    static int binarySearch(int arr[], int data){
        int low=0, high = arr.length-1;
        while(low<=high) {
            int mid = low+(high-low)/2;

            if(arr[mid] == data){
                return mid;
            } else if(arr[mid] < data) {
                low = mid+1;
            } else{
                high = mid-1;
            }
        }
        return -1;
    }

    // recursive binary search
    static int recursiveBinarySearch(int arr[], int data) {
        int low=0, high = arr.length-1;
        return recursiveBinarySearchImpl(arr, low, high, data);
    }
    static int recursiveBinarySearchImpl(int arr[], int low, int high, int data){
        if(high>= low) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == data) {
                return mid;
            } else if(arr[mid]>data) {
                return recursiveBinarySearchImpl(arr, low, mid-1, data);
            } else {
                return recursiveBinarySearchImpl(arr, mid+1, high, data);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        int arr[] = {2, 4, 6, 8, 10, 12, 14};
        int target = 20;

        int iterativeResult = binarySearch(arr, target);
        System.out.println("Data is"+(iterativeResult!=-1?" Found at index: "+iterativeResult:" Not found."));

        int recursiveResult = recursiveBinarySearch(arr, 12);
        System.out.println("Data is"+(recursiveResult!=-1?" Found at index: "+recursiveResult:" Not found."));

    }

}
/************************************************************
 * 🔚 End of BinarySearch.java
 * 📣 Built for mastering iterative & recursive binary search
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

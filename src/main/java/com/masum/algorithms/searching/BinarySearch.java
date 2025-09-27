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
        int left=0, right  = arr.length-1;
        while(left<=right ) {
            int mid = left+(right -left)/2;

            if(arr[mid] == data){
                return mid;
            } else if(arr[mid] < data) {
                left = mid+1;
            } else{
                right  = mid-1;
            }
        }
        return -1;
    }

    // recursive binary search
    static int recursiveBinarySearch(int arr[], int data) {
        int left=0, right  = arr.length-1;
        return recursiveBinarySearchImpl(arr, left, right , data);
    }
    static int recursiveBinarySearchImpl(int arr[], int left, int right , int data){
        if(right >= left) {
            int mid = left + (right  - left) / 2;

            if (arr[mid] == data) {
                return mid;
            } else if(arr[mid]>data) {
                return recursiveBinarySearchImpl(arr, left, mid-1, data);
            } else {
                return recursiveBinarySearchImpl(arr, mid+1, right , data);
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

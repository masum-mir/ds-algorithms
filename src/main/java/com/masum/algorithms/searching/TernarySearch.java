package com.masum.algorithms.searching;
/************************************************************
 * 🔰 CLASS: TernarySearch
 * ----------------------------------------------------------
 * 📌 Description : Ternary search is a divide-and-conquer search algorithm used to find
 *                  the position of a target value within a monotonically increasing or decreasing
 *                  function or in a unimodal array (e.g., U-shaped or ∩-shaped).
 * 🛠️  Purpose    : Efficiently find an element’s index in
 *                  a sorted array by dividing the search space into three parts, reducing
 *                  the number of comparisons.
 * 🧠 Complexity   : O(log₃ n)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 27-Sep-2025
 ************************************************************/

public class TernarySearch {

    // iterative ternary search
    static int ternarySearch(int arr[], int data){
        int left =0, right  = arr.length-1;
        while(left <=right ) {
            int mid1 = left +(right -left )/3;
            int mid2 = right -(right -left )/3;

            if(arr[mid1] == data){
                return mid1;
            }
            if(arr[mid2] == data) {
                return mid2;
            }

            if(data < arr[mid1]) {
                right = mid1-1;
            }else if(data>arr[mid2]) {
                left = mid2+1;
            }else{
                left = mid1 + 1;
                right = mid2 - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        int arr[] = {2, 4, 6, 8, 10, 12, 14};
        int target = 10;

        int iterativeResult = ternarySearch(arr, target);
        System.out.println("Data is"+(iterativeResult!=-1?" Found at index: "+iterativeResult:" Not found."));

    }

}
/************************************************************
 * 🔚 End of TernarySearch.java
 * 📣 Built for mastering ternary search
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

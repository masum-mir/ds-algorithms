package com.algovault.algo.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/************************************************************
 * 🔰 CLASS: BucketSort
 * ----------------------------------------------------------
 * 📌 Description  : Implementation of BucketSort
 * 🛠️  Purpose     : Distribute elements into multiple buckets, sort each
 *                   bucket individually, and then merge all buckets to get a
 *                   fully sorted array.
 *
 * 🧠 Complexity   : O(n²)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 11-Aug-2025
 ************************************************************/

public class BucketSort {

    static void bucketSort(float[] arr, int n) {
        if (n <= 0) return;

        // 1. Create buckets
        @SuppressWarnings("unchecked")
        List<Float>[] buckets = new List[n];
        for (int i = 0; i < n; i++) { // start from 0
            buckets[i] = new ArrayList<>();
        }

        // 2. Distribute elements into buckets
        for (int i = 0; i < n; i++) {
            int bucketIndex = Math.min((int) (arr[i] * n), n - 1);
            buckets[bucketIndex].add(arr[i]);
        }

        // 3. Sort individual buckets
        for (int i = 0; i < n; i++) {
            Collections.sort(buckets[i]);
        }

        // 4. Concatenate all buckets
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (float num : buckets[i]) {
                arr[index++] = num;
            }
        }
    }

    public static void main(String[] args) {
        float[] arr = {0.42f, 0.32f, 0.23f, 0.52f, 0.25f, 0.47f, 0.51f};

        System.out.print("Before BucketSort: ");
        for (float num : arr) System.out.print(num + " ");

        bucketSort(arr, arr.length);

        System.out.print("\nAfter BucketSort: ");
        for (float num : arr) System.out.print(num + " ");
    }
}
/************************************************************
 * 🔚 End of BucketSort.java
 * 📣 Built for mastering bucket sort operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

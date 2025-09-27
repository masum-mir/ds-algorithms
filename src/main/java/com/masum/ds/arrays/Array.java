package com.masum.ds.arrays;

import java.util.Arrays;

/************************************************************
 * 🔰 CLASS: Array
 * ----------------------------------------------------------
 * 📌 Description : Implementation of Queue using Linked List
 * 📦 Features    : Add, Insert, Delete, Search, Traverse, Sort,
 *                  Reverse, Max, Min, binarySearch,recursiveBinarySearch,
 *                  bubbleSort, quickSort
 * 🧠 Complexity  : O(1) for append, O(n) for insert/delete/
 *                  search, O(log n) for binarySearch, O(n^2) for bubblesort,
 *                  O(n^2) for quickSort
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 06-Aug-2025
 ************************************************************/

public class Array {
    private int[] arr;
    private int size;

    // Constructor: initialize with capacity
    public Array(int capacity) {
        arr = new int[capacity];
        size = 0;
    }

    // Resize internal array
    private void resize(int newCapacity) {
        arr = Arrays.copyOf(arr, newCapacity);
    }

    // Add element at the end (like append)
    public void add(int data) {
        if(size == arr.length) {
            resize(arr.length*2);
        }
        arr[size++] = data;
    }

    // Insert at beginning
    public void insertBeginning(int data) {
        insert(0, data);
    }

    // Insert at position
    public void insert(int pos, int data){
        if(pos<0 || pos>size) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }
        if(size == arr.length) {
            resize(arr.length * 2);
        }
        for(int i=size-1; i>=pos; i--) {
            arr[i+1] = arr[i];
        }
        arr[pos] = data;
        size++;
    }

    // Insert multiple element at position
    public void insertMultiple(int pos, int[] data) {
        if(pos<0 || pos>size) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }
        int needResize = size+data.length;
        if(needResize>arr.length) {
            resize(Math.max(arr.length*2, needResize));
        }

        // shift existing elements right to make space
        for(int i=size-1; i>=pos; i--) {
            arr[i+data.length] = arr[i];
        }
        // insert new data
        for(int i=0; i<data.length; i++) {
            arr[pos+i] = data[i];
        }
        size = needResize;
    }

    // Helper: shrink array size if too empty
    private void shrinkIfNeeded() {
        if(size>0 && size == arr.length/4) {
            resize(arr.length/2);
        }
    }

    // delete from end
    public void deleteEnd() {
        if(size==0) {
            throw new IllegalStateException("Array is empty.");
        }
        size--;
        arr[size] = 0;
        shrinkIfNeeded(); //  shrink array size if too empty
    }

    // delete from beginning
    public void deleteBeginning() {
        delete(0);
    }

    // delete from any position
    public void delete(int pos) {
        if(pos<0 || pos>=size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        for(int i=pos; i<size-1; i++) {
            arr[i] = arr[i+1];
        }
        size--;
        arr[size]=0;
        shrinkIfNeeded();
    }

    // delete by element - first occurrence
    public boolean deleteElement(int data) {
        int pos=0;
        for(int i=0;i<size;i++) {
            if(arr[i] == data) {
                pos = i;
            }
        }
        if(pos == -1) {
            return false;
        }
        delete(pos);
        return true;
    }

    // delete all occurrences of position
    public int deleteAllOccurrences(int data) {
        int count =0;
        for(int i=0;i<size;) {
            if(arr[i] == data) {
                delete(i);
                count++;
            } else {
                i++;
            }
        }
        return count;
    }

    // search for element - linear search
    public boolean search(int data) {
        for(int i=0;i<size; i++) {
            if(arr[i]==data) {
                return true;
            }
        }
        return false;
    }

    // binary search
    public int binarySearch(int arr[], int data){
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
    // recursive binarySearch
    public int recursiveBinarySearch(int arr[], int data) {
        int low=0, high = arr.length-1;
        return recursiveBinarySearchImpl(arr, low, high, data);
    }
    private  int recursiveBinarySearchImpl(int arr[], int low, int high, int data){
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

    // get element at position
    public int get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Invalid position.");
        }
        return arr[pos];
    }

    //set/update element at position
    public void set(int pos, int data) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        arr[pos] = data;
    }

    // print current array elements
    public void traverse() {
        System.out.print("Array elements: ");
        for(int i=0;i<size;i++) {
            System.out.print(arr[i]+" ");
        }
    }

    // sort the array build in library
    public void sort() {
        Arrays.sort(arr, 0, size);
    }

    // bubble sort impl
    public void bubbleSort(int arr[], int n) {
        int i, j, temp, flag=0;
        for(i=0;i<n-1;i++) {
            flag=0;
            for(j=0;j<n-i-1; j++) {
                if(arr[j] > arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    flag = 1;
                }
            }
            if(flag==0) {
                break;
            }
        }
    }

    // quick sort impl
    public void quickSort(int arr[], int low, int high) {
        if(low<high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
    }
    private int partition(int arr[], int low, int high) {
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

    private void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // reverse the array
    public void reverse() {
        for(int i=0;i<size/2; i++) {
            int temp = arr[i];
            arr[i] = arr[size-1-i];
            arr[size-1-i] = temp;
        }
    }

    // find max element
    public int max() {
        if(size == 0) {
            throw new IllegalStateException("Array is empty");
        }
        int max = arr[0];
        for(int i=1;i<size; i++) {
            if(arr[i]>max) {
                max = arr[i];
            }
        }
        return max;
    }

    // find min element
    public int min() {
        if(size == 0) {
            throw new IllegalStateException("Array is empty");
        }
        int min = arr[0];
        for(int i=1;i<size; i++) {
            if(arr[i]<min) {
                min = arr[i];
            }
        }
        return min;
    }

    // copy array build in library
    public int[] copy() {
        return Arrays.copyOf(arr, size);
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀
        Array arr = new Array(5);
        arr.add(10);
        arr.add(20);
        arr.add(30);
        arr.add(40);
        arr.add(50);
        arr.traverse();

        System.out.println("\nInsert at Beginning (5): ");
        arr.insertBeginning(5);
        arr.traverse();

        System.out.println("\nInsert at Position (2, 15): ");
        arr.insert(2, 15);
        arr.traverse();

        System.out.println("\nInsert Multiple at Position (3, {17, 18}): ");
        arr.insertMultiple(3, new int[]{17, 18});
        arr.traverse();

        System.out.println("\nDelete from End");
        arr.deleteEnd();
        arr.traverse();

        System.out.println("\nDelete from Beginning: ");
        arr.deleteBeginning();
        arr.traverse();

        System.out.println("\nDelete from Position 3: ");
        arr.delete(3);
        arr.traverse();

        System.out.println("\nDelete by Element (20): ");
        arr.deleteElement(20);
        arr.traverse();

        System.out.println("\nAdd Duplicate Elements for Testing deleteAllOccurrences: ");
        arr.add(30);
        arr.add(30);
        arr.traverse();

        System.out.println("\nDelete All Occurrences of (30): ");
        int deleted = arr.deleteAllOccurrences(30);
        System.out.println("Deleted " + deleted + " occurrence(s): ");
        arr.traverse();

        System.out.println("\nSearch for 15: " + arr.search(15));
        System.out.println("\nSearch for 99: " + arr.search(99));

        System.out.println("\nSort the Array (Built-in): ");
        arr.sort();
        arr.traverse();

        System.out.println("\nReverse the Array: ");
        arr.reverse();
        arr.traverse();

        System.out.println("\nMax Element: " + arr.max());
        System.out.println("\nMin Element: " + arr.min());

        System.out.println("\nBinary Search (on sorted copy for 15): ");
        int[] sortedCopy = arr.copy();
        arr.bubbleSort(sortedCopy, sortedCopy.length); // ensure sorted
        System.out.println("\nBinary Search result: " + arr.binarySearch(sortedCopy, 15));

        System.out.println("\nRecursive Binary Search (for 10): ");
        System.out.println("\nRecursive Binary Search result: " + arr.recursiveBinarySearch(sortedCopy, 10));

        System.out.println("\nBubble Sort (on copy): ");
        int[] bubbleCopy = arr.copy();
        arr.bubbleSort(bubbleCopy, bubbleCopy.length);
        System.out.print("After Bubble Sort: ");
        for (int val : bubbleCopy) {
            System.out.print(val + " ");
        }

        System.out.println("\nQuick Sort (on copy): ");
        int[] quickCopy = arr.copy();
        arr.quickSort(quickCopy, 0, quickCopy.length - 1);
        System.out.print("After Quick Sort: ");
        for (int val : quickCopy) {
            System.out.print(val + " ");
        }

    }

}
/************************************************************
 * 🔚 End of QueueWithLinkList.java
 * 📣 Built for mastering DSA concepts step by step
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/
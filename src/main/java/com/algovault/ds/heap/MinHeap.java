package com.algovault.ds.heap;

/************************************************************
 * 🔰 CLASS: MinHeap
 * ----------------------------------------------------------
 * 📌 Description :
 *
 * 🛠️  Purpose     :
 *
 * 🧠 Complexity   :
 *                  Insert   -
 *                  Delete   -
 *                  Search   -
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 08-Aug-2025
 ************************************************************/
public class MinHeap {
    private int[] heap;
    private int size;
    private int capacity;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }
    private int left(int i) {
        return 2 * i + 1;
    }
    private int right(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void insert(int value) {
        if (size == capacity) {
            System.out.println("Heap is full!");
            return;
        }

        heap[size] = value;
        int current = size;
        size++;

        while (current > 0 && heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public int delete() {
        if (size <= 0) throw new RuntimeException("Heap is empty!");

        int root = heap[0];
        heap[0] = heap[size - 1];
        size--;

        heapify(0);  // Restore heap property
        return root;
    }

    private void heapify(int i) {
        int smallest = i;
        int l = left(i);
        int r = right(i);

        if (l < size && heap[l] < heap[smallest]) smallest = l;
        if (r < size && heap[r] < heap[smallest]) smallest = r;

        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        MinHeap h = new MinHeap(10);

        h.insert(50);
        h.insert(30);
        h.insert(40);
        h.insert(10);
        h.insert(60);

        System.out.print("Heap after insert: ");
        h.printHeap();

        System.out.println("Deleted root: " + h.delete());
        System.out.print("Heap after delete: ");
        h.printHeap();
    }
}
/************************************************************
 * 🔚 End of MinHeap.java
 * 📣 Built for mastering DSA concepts step by step
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/
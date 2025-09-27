package com.masum.ds.queue;

/************************************************************
 * 🔰 CLASS: QueueWithLinkList
 * ----------------------------------------------------------
 * 📌 Description : Implementation of Queue using Linked List
 * 🛠️  Purpose     : Enqueue, Dequeue, Peek, and Traverse queue
 * 🧠 Complexity   : O(1) enqueue/dequeue operations
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 06-Aug-2025
 ************************************************************/

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        next = null;
    }
}

public class QueueWithLinkList {
    private Node front;
    private Node rear;

    public QueueWithLinkList() {
        front = null;
        rear = null;
    }

    // Add element at rear of the queue
    public void enqueue(int data) {
        Node newNode = new Node(data);
        if(front == null) {  // Queue empty
            front = rear = newNode;
            return;
        }
        rear.next = newNode;
        rear = newNode;
    }

    // Remove element from front of the queue
    public void dequeue() {
        if(front == null) {
            return;  // Queue empty, nothing to dequeue
        }
        front = front.next;
        if(front == null) {
            rear = null;  // Queue became empty
        }
    }

    // Return front element data without removing it
    public int peek() {
        if(front == null) {
            return -1;  // Queue empty
        }
        return front.data;
    }

    // Print all elements from front to rear
    public void traverse() {
        if(front == null) {
            System.out.println("Queue is empty.");
            return;
        }
        Node curr = front;
        System.out.print("Queue elements: ");
        while(curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println(); // New line after printing all
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        QueueWithLinkList qql = new QueueWithLinkList();
        qql.enqueue(10);
        qql.enqueue(20);
        qql.enqueue(30);
        qql.traverse();
        System.out.println("Peek: " + qql.peek());
        qql.dequeue();
        qql.traverse();
    }
}

/************************************************************
 * 🔚 End of QueueWithLinkList.java
 * 📣 Simple linked list based queue with efficient ops
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

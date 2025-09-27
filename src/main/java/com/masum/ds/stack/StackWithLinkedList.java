package com.masum.ds.stack;

/************************************************************
 * 🔰 CLASS: StackWithLinkedList
 * ----------------------------------------------------------
 * 📌 Description : Stack implementation using singly linked list
 * 🛠️  Purpose     : push, pop, peek, and traversal operations
 * 🧠 Complexity   : O(1) for push, pop, peek; O(n) for traversal
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

public class StackWithLinkedList {
    private Node top;

    // Initialize an empty stack
    public StackWithLinkedList() {
        top = null;
    }

    // Insert element at the top of the stack
    public void push(int data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
    }

    // Remove and return the top element; return -1 if stack is empty
    public int pop() {
        if(top == null) {
            return -1; // Indicates stack underflow
        }
        int removeData = top.data;
        top = top.next;
        return removeData;
    }

    // Return the top element without removing; -1 if empty
    public int peek() {
        if(top == null) {
            return -1;
        }
        return top.data;
    }

    // Print stack elements from top to bottom
    public void traverse() {
        if(top == null) {
            System.out.println("Stack is empty.");
            return;
        }
        System.out.print("Stack elements: ");
        Node curr = top;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        StackWithLinkedList sll = new StackWithLinkedList();
        sll.push(20);
        sll.push(56);
        sll.push(30);
        sll.traverse();

        System.out.println("Top element: " + sll.peek());
        System.out.println("Popped: " + sll.pop());
        sll.traverse();
    }
}

/************************************************************
 * 🔚 End of StackWithLinkedList.java
 * 📣 Built for mastering DSA concepts step by step
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

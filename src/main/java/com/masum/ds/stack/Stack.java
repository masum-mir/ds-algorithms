package com.masum.ds.stack;

/************************************************************
 * 🔰 CLASS: Stack
 * ----------------------------------------------------------
 * 📌 Description : Array-based implementation of Stack data structure
 * 🛠️  Purpose     : push, pop, peek, search, and traversal
 * 🧠 Complexity   : O(1) for push/pop/peek, O(n) for search/traversal
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 06-Aug-2025
 ************************************************************/

public class Stack {
    int top;           // Index of the top element in the stack
    int capacity;      // Maximum capacity of the stack
    int[] stack;       // Array to hold stack elements

    public Stack(int capacity) {
        this.capacity = capacity;
        top = -1;             // Empty stack indicated by top = -1
        stack = new int[capacity];
    }

    // Add element to the top of the stack
    public void push(int data) {
        if(top >= capacity - 1) {
            System.out.println("Stack Overflow");
            return;
        }
        stack[++top] = data;
    }

    // Remove and return top element of the stack
    public int pop() {
        if(top < 0) {
            System.out.println("Stack Underflow.");
            return 0;  // Could throw exception instead
        }
        return stack[top--];
    }

    // Return top element without removing
    public int peek() {
        if(top < 0) {
            System.out.println("Stack is Empty");
            return 0;
        }
        return stack[top];
    }

    // Check if stack is empty
    public boolean isEmpty() {
        return top == -1;
    }

    // Check if stack is full
    public boolean isFull() {
        return top == capacity - 1;
    }

    // Return current number of elements in stack
    public int size() {
        return top + 1;
    }

    // Remove all elements from stack
    public void clear() {
        top = -1;
        System.out.println("Stack cleared.");
    }

    // Return 1-based position from top if found, else -1
    public int search(int data) {
        for(int i = top; i >= 0; i--) {
            if(stack[i] == data) {
                return top - i + 1;  // Distance from top
            }
        }
        return -1;  // Not found
    }

    // Print all stack elements from bottom to top
    public void traverse() {
        if(isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }
        System.out.print("Stack elements: ");
        for(int i = 0; i <= top; i++) {
            System.out.print(stack[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        Stack stack = new Stack(5);
        stack.push(10);
        stack.push(20);
        stack.traverse();
        stack.pop();
        stack.traverse();

        System.out.println("Search for 10: " + stack.search(10));
        System.out.println("Search for 30: " + stack.search(30));

    }
}

/************************************************************
 * 🔚 End of Stack.java
 * 📣 Built for mastering DSA concepts step by step
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

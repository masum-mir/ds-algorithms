package com.masum.ds.stack;

import java.util.LinkedList;
import java.util.Queue;

/************************************************************
 * 🔰 CLASS: StackUsingQueues
 * ----------------------------------------------------------
 * 📌 Description : Implementing Stack operations using two Queues
 * 🛠️  Purpose     : Simulate LIFO behavior with FIFO data structures
 * 🧠 Complexity   : push: O(1), pop: O(n), peek: O(n)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 06-Aug-2025
 ************************************************************/

public class StackUsingQueues {
    private Queue<Integer> queue1;
    private Queue<Integer> queue2;

    public StackUsingQueues() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    // Push element onto stack
    public void push(int data) {
        queue1.offer(data);
    }

    // Pop element from stack
    public int pop() {
        if(queue1.isEmpty()) {
            System.out.println("Stack is empty.");
            return -1;
        }
        while(queue1.size() > 1) {
            queue2.offer(queue1.poll());
        }

        int topElement = queue1.poll();

        Queue<Integer> temp = queue1;
        queue1 = queue2;
        queue2 = temp;

        return topElement;
    }

    // Peek top element without removing
    public int peek() {
        if(queue1.isEmpty()) {
            System.out.println("Stack is empty.");
            return -1;
        }
        // Move all elements except last from queue1 to queue2
        while (queue1.size()>1) {
            queue2.offer(queue1.poll());
        }
        // Last element is the top of the stack
        int topElement = queue1.poll();

        // Add it back to queue2
        queue2.offer(topElement);

        // Swap queues
        Queue<Integer> temp = queue1;
        queue1 = queue2;
        queue2 = temp;

        return topElement;
    }

    // Traverse stack elements
    public void traverse() {
        if(queue1.isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }

        System.out.print("Stack elements: ");
        Integer[] arr = queue1.toArray(new Integer[0]);
        // Since queue1 represents stack bottom to top,
        // we need to collect elements and print in reverse
        for(int i=arr.length-1; i>=0; i--) {
            System.out.print(arr[i]+" ");
        }
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        StackUsingQueues s = new StackUsingQueues();
        s.push(10);
        s.push(20);
        s.push(30);
        s.traverse();
        System.out.println("Pop: " + s.pop());
        s.traverse();
        System.out.println("Top: " + s.peek());
        s.push(40);
        s.traverse();
    }

}
/************************************************************
 * 🔚 End of StackUsingQueues.java
 * 📣 Built for mastering DSA concepts step by step
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

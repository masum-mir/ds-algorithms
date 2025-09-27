package com.algovault.ds.linkedlist;

/************************************************************
 * 🔰 CLASS: SinglyLinkedList
 * ----------------------------------------------------------
 * 📌 Description : Implementation of Singly Linked List
 * 🛠️  Purpose     : Insert, Delete, Search, Reverse, and other
 *                   linked list operations
 * 🧠 Complexity   : Mostly O(n), some O(1) operations
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 05-Aug-2025
 ************************************************************/

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class SinglyLinkedList {
    Node head;

    // Display all nodes in the list
    public void display() {
        Node current = head;
        while(current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }

    // Insert at beginning (head)
    public void insertAtBeginning(int data) {
        System.out.println("Head:: " + head);
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    // Insert at end (tail)
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);
        if(head == null) {
            head = newNode;
            return;
        }
        Node current = head;
        while(current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    // Insert at given position (0-based)
    public void insertAtPosition(int data, int pos) {
        if(pos == 0) {
            insertAtBeginning(data);
            return;
        }

        System.out.println("Head: " + head);
        Node current = head;
        int i = 0;
        while(i < pos - 1 && current != null) {
            current = current.next;
            i++;
        }

        if(current == null) {
            System.out.println("Invalid position.");
            return;
        }

        Node newNode = new Node(data);
        newNode.next = current.next;
        current.next = newNode;
    }

    // Insert after a given node
    public void insertAfterNode(Node preNode, int data) {
        if(preNode == null) return;

        Node newNode = new Node(data);
        newNode.next = preNode.next;
        preNode.next = newNode;
    }

    // Delete node from beginning
    public void deleteFromBeginning() {
        if(head == null) return;
        head = head.next;
    }

    // Delete node from end
    public void deleteFromEnd() {
        if(head == null) return;
        if(head.next == null) {
            head = null;
            return;
        }
        Node current = head;
        while(current.next.next != null) {
            current = current.next;
        }
        current.next = null;
    }

    // Delete node at a specific position
    public void deleteAtPosition(int pos) {
        if(head == null) return;

        if(pos == 0) {
            head = head.next;
            return;
        }

        Node current = head;
        for(int i = 0; i < pos - 1 && current.next != null; i++) {
            current = current.next;
        }

        if(current.next == null) {
            System.out.println("Invalid position.");
            return;
        }

        current.next = current.next.next;
    }

    // Delete node by value
    public void deleteByValue(int value) {
        if(head == null) return;
        if(head.data == value) {
            head = head.next;
            return;
        }

        Node current = head;
        while(current.next != null && current.next.data != value) {
            current = current.next;
        }

        if(current.next != null) {
            current.next = current.next.next;
        }
    }

    // Reverse linked list (iterative)
    public void reverse() {
        Node prev = null;
        Node current = head;
        Node next = null;

        while(current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    // Reverse linked list (recursive)
    public void reverseRecursive() {
        head = reverseRecursiveUtil(head);
    }
    private Node reverseRecursiveUtil(Node current) {
        if(current == null || current.next == null) {
            return current;
        }
        Node newHead = reverseRecursiveUtil(current.next);
        current.next.next = current;
        current.next = null;
        return newHead;
    }

    // Search for a value, return true if found
    public boolean search(int value) {
        Node current = head;
        while(current != null) {
            if(current.data == value) return true;
            current = current.next;
        }
        return false;
    }

    // Get element at position (0-based)
    public int getElementAt(int pos) {
        Node current = head;
        int index = 0;
        while(current != null) {
            if(index == pos) return current.data;
            current = current.next;
            index++;
        }
        throw new IndexOutOfBoundsException("Position out of bounds");
    }

    // Detect if cycle exists in linked list - Floyd's cycle Algorithm
    public boolean hasCycle() {
        Node slow = head;
        Node fast = head;

        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(fast == slow) return true;
        }
        return false;
    }

    // Find middle node of linked list
    public Node findMiddle() {
        Node slow = head, fast = head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // Remove cycle from linked list if present
    public void removeCycle() {
        Node slow = head, fast = head;

        // Detect cycle
        do {
            if(fast == null || fast.next == null) return;
            slow = slow.next;
            fast = fast.next.next;
        } while(slow != fast);

        // Find start of cycle
        slow = head;
        if(fast == slow) {
            while(fast.next != slow) fast = fast.next;
        } else {
            while(slow.next != fast.next) {
                slow = slow.next;
                fast = fast.next;
            }
        }
        fast.next = null;
    }

    // Get nth node from end
    public int getNthNodeFormEnd(int n) {
        Node first = head, second = head;
        for(int i = 0; i < n; i++) {
            if(first == null) throw new IllegalArgumentException("N is too large");
            first = first.next;
        }
        while(first != null) {
            first = first.next;
            second = second.next;
        }
        return second.data;
    }

    // Merge two sorted linked lists and return head of merged list
    public Node mergeSorted(Node l1, Node l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;

        if(l1.data < l2.data) {
            l1.next = mergeSorted(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeSorted(l1, l2.next);
            return l2;
        }
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        SinglyLinkedList list = new SinglyLinkedList();

        list.insertAtEnd(10);
        list.insertAtEnd(20);
        list.insertAtBeginning(5);
        list.insertAtBeginning(50);
        list.insertAtEnd(5);
        list.insertAtPosition(15, 1);
        list.insertAfterNode(list.head.next, 25);

        System.out.print("Original List: ");
        list.display();

        list.deleteFromBeginning();
        System.out.print("\nAfter Deletion at beginning: ");
        list.display();

        list.deleteFromEnd();
        System.out.print("\nAfter Deletion at end: ");
        list.display();

        list.deleteByValue(15);
        System.out.print("\nAfter Deletion at value: ");
        list.display();

        list.deleteAtPosition(2);
        System.out.print("\nAfter Deletion at Position 2: ");
        list.display();

        list.reverse();
        System.out.print("\nReversed List: ");
        list.display();

        list.reverseRecursive();
        System.out.print("\nRecursive Reversed List: ");
        list.display();

        System.out.print("\nSearch 20: " + list.search(20));
        System.out.print("\nElement at position 0: " + list.getElementAt(0));
        System.out.print("\nCycle Detected: " + list.hasCycle());

    }
}

/************************************************************
 * 🔚 End of SinglyLinkedList.java
 * 📣 Built for mastering linked list operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

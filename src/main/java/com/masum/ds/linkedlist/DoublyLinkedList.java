package com.masum.ds.linkedlist;

/************************************************************
 * 🔰 CLASS: DoublyLinkedList
 * ----------------------------------------------------------
 * 📌 Description : Implementation of Doubly Linked List
 * 🛠️  Purpose     : Insert, Delete, Search, Traversal (both ways),
 *                   Size, and other doubly linked list operations
 * 🧠 Complexity   : Mostly O(n), some O(1) operations
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 05-Aug-2025
 ************************************************************/

class NodeD {
    int data;
    NodeD prev;
    NodeD next;

    NodeD(int data) {
        this.data = data;
        prev = null;
        next = null;
    }
}

public class DoublyLinkedList {
    NodeD head;
    NodeD tail;

    // Traverse forward iteratively and print all node data
    public void forwardTraversal() {
        NodeD curr = head;
        while(curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
    }

    // Traverse forward recursively and print all node data
    public void forwardTraversalRecursive() {
        forwardTraversalRecursiveImpl(head);
    }
    private void forwardTraversalRecursiveImpl(NodeD head) {
        if(head == null) return;
        System.out.println(head.data + " ");
        forwardTraversalRecursiveImpl(head.next);
    }

    // Traverse backward iteratively and print all node data
    public void backwardTraversal() {
        NodeD curr = tail;
        while(curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.prev;
        }
    }

    // Traverse backward recursively and print all node data
    public void backwardTraversalRec() {
        backwardTraversalRecImpl(tail);
    }
    private void backwardTraversalRecImpl(NodeD tail) {
        if(tail == null) return;
        System.out.println(tail.data + " ");
        backwardTraversalRecImpl(tail.prev);
    }

    // Find size of list iteratively
    public int findSize(NodeD head) {
        NodeD curr = head;
        int size = 0;
        while(curr != null) {
            size++;
            curr = curr.next;
        }
        return size;
    }

    // Find size of list recursively
    public int findSizeRec(NodeD head) {
        if(head == null) return 0;
        return 1 + findSizeRec(head.next);
    }

    // Insert new node at front (head)
    public void insertAtFront(int data) {
        NodeD newNode = new NodeD(data);
        newNode.next = head;
        if(head != null) {
            head.prev = newNode;
        } else {
            tail = newNode; // If list was empty
        }
        head = newNode;
    }

    // Insert new node at end (tail)
    public void insertAtEnd(int data) {
        NodeD newNode = new NodeD(data);
        if(head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Insert new node at given 1-based position
    public void insertAtPosition(int pos, int data) {
        if(pos <= 0) return;
        if(pos == 1) {
            insertAtFront(data);
            return;
        }

        NodeD newNode = new NodeD(data);
        NodeD curr = head;
        for(int i = 1; i < pos - 1 && curr != null; i++) {
            curr = curr.next;
        }

        if(curr == null || curr.next == null) {
            insertAtEnd(data);
            return;
        }

        newNode.next = curr.next;
        newNode.prev = curr;
        curr.next.prev = newNode;
        curr.next = newNode;
    }

    // Delete node from front (head)
    public void deleteAtFront() {
        if(head == null) return;
        if(head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
    }

    // Delete node from end (tail)
    public void deleteFromEnd() {
        if(tail == null) return;
        if(head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
    }

    // Delete node by value (first occurrence)
    public void deleteByValue(int data) {
        if(head == null) return;
        NodeD curr = head;

        if(curr.data == data) {
            deleteAtFront();
            return;
        }

        while(curr != null && curr.data != data) {
            curr = curr.next;
        }
        if(curr == null) {
            System.out.println("Value not found.");
            return;
        }

        if(curr == tail) {
            deleteFromEnd();
            return;
        }

        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
    }

    // Search for a value, return true if found
    public boolean search(int data) {
        NodeD curr = head;
        while(curr != null) {
            if(curr.data == data) return true;
            curr = curr.next;
        }
        return false;
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        DoublyLinkedList dll = new DoublyLinkedList();

        dll.insertAtFront(20);
        dll.insertAtEnd(30);
        dll.insertAtFront(10);
        dll.insertAtEnd(40);

        System.out.print("After inserts: ");
        dll.forwardTraversal();

        dll.insertAtPosition(3, 25);
        System.out.print("\nAfter insertAtPosition(3, 25): ");
        dll.forwardTraversal();

        // Search
        System.out.print("\nSearching for 25: " + dll.search(25));
        System.out.print("\nSearching for 50: " + dll.search(50));

        // Delete by value
        dll.deleteByValue(25);
        System.out.print("\nAfter deleteByValue(25): ");
        dll.forwardTraversal();

        // Delete from front
        dll.deleteAtFront();
        System.out.print("\nAfter deleteAtFront(): ");
        dll.forwardTraversal();

        // Delete from end
        dll.deleteFromEnd();
        System.out.print("\nAfter deleteFromEnd(): ");
        dll.forwardTraversal();

        // Backward traversal
        System.out.print("\nBackward Traversal: ");
        dll.backwardTraversal();
    }
}

/************************************************************
 * 🔚 End of DoublyLinkedList.java
 * 📣 Built for mastering doubly linked list operations stepwise
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

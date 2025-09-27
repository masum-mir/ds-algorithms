package com.masum.ds.tree;
/************************************************************
 * 🔰 CLASS: BTree
 * ----------------------------------------------------------
 * 📌 Description : A B-tree is a self-balancing, multi-way search tree data structure.
 *                   insertion -
 *                   1. follow BST when insert data
 *                   2. leaf nodes are same level
 *                   3. every node max(m-1) key and m children
 *                   4. data insert small to large
 *                   Note: if order is 2 then max(2m-1) key
 *                   deletion -
 *                   1. minimum no. of keys floor(m/2)-1
 *                   2. leaf node -
 *                      * minimum no. of key is high then delete node
 *                      * minimum no of key then
 *                          -> immediate left sibling from max data
 *                          -> immediate right sibling from min data
 *                          Note: if minimum no. of keys is high
 *                   3. if left sibling and right sibling are minimum no. of keys then marge
 *
 *
 * 🛠️  Purpose     : It is designed to maintain sorted data
 *                  and efficiently handle searches, sequential access, insertions,
 *                  and deletions, typically in logarithmic time. B-trees are particularly
 *                  well-suited for database systems and file systems due to their optimization
 *                  for disk I/O operations.
 *
 * 🧠 Complexity   :
 *                  Insert   - O(log n)
 *                  Delete   - O(log n)
 *                  Search   - O(log n)
 *                  Traversal - O(n)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 10-Aug-2025
 ************************************************************/
class BTreeNode {
    int[] keys; // keys
    int order;  // range for number of keys
    BTreeNode[] child;
    int n;  // current number of key
    boolean leaf;

    public BTreeNode(int order, boolean leaf) {
        this.keys = new int[2*order-1];
        this.order = order;
        this.child = new BTreeNode[2*order];
        this.n = 0;
        this.leaf =leaf;
    }

    public void traverse() {
        int i;
        for(i=0;i<n;i++) {
            //  visit the child before printing the key
            if(!leaf) {
                child[i].traverse();
            }
            System.out.print(keys[i]+" ");
        }

        // print the subtree root with last child
        if(!leaf) {
            child[i].traverse();
        }
    }

    // search key in subtree rooted with this node
    public BTreeNode search(int data) {
        int i=0;
        while (i<n && data > keys[i]) {
            i++;
        }
        if(i<n && keys[i] == data) {
            return this;
        }
        if(leaf) {
            return null;
        }
        return child[i].search(data);
    }

    // insert a new data in the subtree rooted with this node
    public void insertNotNull(int data) {
        int i = n-1;
        if(leaf) {
            // find the location where insert
            while (i>= 0 && keys[i] > data) {
                keys[i+1] = keys[i];
                i--;
            }
            keys[i+1] = data;
            n++;
        } else  {
            // find the child to insert into
            while (i>=0 && keys[i] > data){
                i--;
            }
            if(child[i+1].n == 2*order-1) {
                splitChild(i+1, child[i+1]);
                if(keys[i+1] < data) {
                    i++;
                }
            }
            child[i+1].insertNotNull(data);
        }
    }
    // split child y of this node
    public void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.order, y.leaf);
        z.n = order-1;

        // copy last (t-1) keys from y to z
        for(int j=0; j<order-1; j++) {
            z.keys[j] = y.keys[j+order];
        }

        // copy child if not leaf
        if(!y.leaf) {
            for(int j=0; i<order; j++) {
                z.child[j] = y.child[j+order];
            }
        }
        y.n = order-1;

        //create space for new child
        for(int j=n; j>=i+1; j--) {
            child[j+1] = child[j];
        }
        child[i+1] = z;

        // move middle key of y up
        for(int j=n-1; j>=i;j--) {
            keys[j+1] = keys[j];
        }
        keys[i] = y.keys[order-1];
        n++;
    }
}

public class BTree {
    BTreeNode root;
    int t; // minimum degree
    public BTree(int t) {
        this.root = null;
        this.t = t;
    }

    public void traverse() {
        if(root!= null) {
            root.traverse();
        }
    }

    public BTreeNode search(int data) {
        return root == null ? null : root.search(data);
    }

    public void insert(int data) {
        if(root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = data;
            root.n = 1;
        } else {
            if(root.n == 2*t-1) {
                BTreeNode s = new BTreeNode(t, false);
                s.child[0] = root;
                s.splitChild(0, root);

                int i=0;
                if(s.keys[0] < data) {
                    i++;
                }
                s.child[i].insertNotNull(data);
                root = s;
            } else {
                root.insertNotNull(data);
            }
        }
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        BTree tree = new BTree(3);
        int[] data = {6, 2, 4, 15, 18, 8, 27, 9,10, 15, 12, 17};
        for(int x: data) {
            tree.insert(x);
        }

        System.out.print("Traversal of B-Tree: ");
        tree.traverse();

        int value = 6;
        System.out.println("\nSearching value "+value+(tree.search(value)!=null ? " is found.": "not found."));

    }
}
/************************************************************
 * 🔚 End of Tree.java
 * 📣 Built for mastering DSA concepts step by step
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

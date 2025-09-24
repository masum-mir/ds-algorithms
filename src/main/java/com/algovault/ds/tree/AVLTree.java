package com.algovault.ds.tree;

/************************************************************
 * 🔰 CLASS: Tree
 * ----------------------------------------------------------
 * 📌 Description : Tree implementation of Tree data structure
 *                  AVL Tree is a self-balancing Binary Search Tree (BST) where the
 *                  difference in heights (balance factor) of left and right subtrees
 *                  of any node is at most 1. If it becomes more than 1 after insertion or deletion,
 *                  the tree performs rotations to restore balance.
 *
 * 🛠️  Purpose     : To efficiently manage dynamic datasets that require fast lookups,
 *                   insertions, and deletions while maintaining balance to ensure
 *                   optimal performance in all operations with ASCII Tree visualization.
 *
 * 🧠 Complexity   :
 *                  Insert   - O(log n)
 *                  Delete   - O(log n)
 *                  Search   - O(log n)
 *                  Rotation - O(1)
 *                  Traversal (In-order) - O(n)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 08-Aug-2025
 ************************************************************/
class AVLNode {
    int data;
    int height;
    AVLNode left;
    AVLNode right;
    public AVLNode(int data) {
        this.data= data;
        this.height = 1;
    }
}

public class AVLTree {
    private AVLNode root;

    public int height(AVLNode root) {
        return root == null ? 0 : root.height;
    }

    public int getBalance(AVLNode root) {
        return root == null ? 0: height(root.left) - height(root.right);
    }

    // right rotate
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode t2 = x.right;

        x.right = y;
        y.left = t2;

        // update heights
        y.height= Math.max(height(y.left), height(y.right))+1;
        x.height= Math.max(height(x.left), height(x.right))+1;

        return x;
    }

    // left rotate
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode t2 = y.left;

        y.left = x;
        x.right = t2;

        //update heights
        x.height = Math.max(height(x.left), height(x.right))+1;
        y.height = Math.max(height(y.left), height(y.right))+1;

        return y;
    }

    // insert a data
    public void insert(int data) {
        root = insert(root, data);
    }
    private AVLNode insert(AVLNode root, int data) {
        if(root == null) {
            return new AVLNode(data);
        }
        if(data<root.data){
            root.left = insert(root.left, data);
        } else if(data>root.data){
            root.right = insert(root.right, data);
        } else{
            return root;
        }

        root.height = 1+Math.max(height(root.left), height(root.right));
        int balance = getBalance(root);

        // rotations
        if(balance > 1 && data < root.left.data) {
            return rightRotate(root); // ll
        }
        if(balance < -1 && data > root.right.data) {
            return  leftRotate(root); // rr
        }
        if(balance>1 && data>root.left.data) { //lr
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if(balance<-1 && data < root.right.data) { // rl
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // delete a data
    public void delete(int data) {
        root = delete(root,data);
    }
    private AVLNode delete(AVLNode root, int data) {
        if(root == null) return root;
        if(data<root.data) {
            root.left = delete(root.left, data);
        } else if(data > root.data) {
            root.right = delete(root.right, data);
        } else {
            if (root.left == null || root.right == null) {
                root = (root.left!= null) ? root.left : root.right;
            } else  {
                AVLNode temp = minValueNode(root.right);
                root.data = temp.data;
                root.right = delete(root.right, temp.data);
            }
        }

        if(root == null) return null;

        root.height= 1+Math.max(height(root.left), height(root.right));
        int balance = getBalance(root);

        // balance tree
        if(balance>1 && getBalance(root.left) >= 0) {
            return rightRotate(root); // ll
        }
        if(balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left); // lr
            return rightRotate(root);
        }
        if(balance<-1 && getBalance(root.right) <=0) {
            return leftRotate(root); //rr
        }
        if(balance<-1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root); //rl
        }
        return root;
    }
    private AVLNode minValueNode(AVLNode root) {
        AVLNode curr = root;
        while(curr.left !=null) {
            curr = curr.left;
        }
        return curr;
    }

    // search a data
    public boolean search(int data) {
        return search(root, data);
    }
    private boolean search(AVLNode root, int data) {
        if(root == null) return false;
        if(data == root.data) return true;
        return data < root.data ? search(root.left, data) : search(root.right, data);
    }

    // inorder traversal
    public void inOrder() {
        inOrder(root);
    }
    private void inOrder(AVLNode root) {
        if(root != null) {
            inOrder(root.left);
            System.out.print(root.data +" ");
            inOrder(root.right);
        }
    }

    // ASCII Tree visualizer --------- this part copy from other resoures
    public void asciiVisualizer() {
        asciiVisualizer(root,"", true);
    }
    private void asciiVisualizer(AVLNode root, String prefix, boolean isTail) {
        if(root == null) return;
        System.out.println(prefix+(isTail ? "└── " : "├──")+root.data);

        if(root.left != null || root.right != null) {
            if(root.right != null) {
                asciiVisualizer(root.right, prefix+(isTail ? "    " : "│   "), false);
            }
            if(root.left != null) {
                asciiVisualizer(root.left, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        AVLTree avlTree = new AVLTree();

        //ll rotation
        avlTree.insert(30);
        avlTree.insert(20);
        avlTree.insert(10);

        // rr rotation
        avlTree.insert(40);
        avlTree.insert(50);

        // lr rotation
        avlTree.insert(25);

        // rl rotation
        avlTree.insert(45);

        avlTree.inOrder();
        System.out.println();

        avlTree.delete(10);
        avlTree.delete(30);
        avlTree.inOrder();

        System.out.println("\nValue is found: "+avlTree.search(40));

        avlTree.insert(340);
        avlTree.insert(2530);
        avlTree.insert(1450);
        avlTree.insert(33450);
        avlTree.insert(240);
        avlTree.insert(160);

        avlTree.asciiVisualizer();
    }

}
/************************************************************
 * 🔚 End of Tree.java
 * 📣 Built for mastering DSA concepts step by step
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/
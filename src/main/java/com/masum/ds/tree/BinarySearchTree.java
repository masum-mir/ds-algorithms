package com.masum.ds.tree;

/************************************************************
 * 🔰 CLASS: Tree
 * ----------------------------------------------------------
 * 📌 Description : Tree implementation of Tree data structure
 * 🛠️  Purpose     : Provides BST insert/search/delete and traversal
 * 🧠 Complexity   :
 *      - insert/search/delete: O(h) where h = tree height (O(log n) balanced, O(n) worst)
 *      - inOrder/preOrder/postOrder traversal: O(n)
 *      - levelOrder traversal: O(n^2) (your version), O(n) with queue
 *      - height calculation: O(n)
 *      - count nodes/leaves: O(n)
 *      - min/max value retrieval: O(h)
 *      - isBST, isBalanced, isComplete, isPerfect, isFull: O(n)
 *
 * 🧑‍💻 Crafted With Logic & Love by Masum | 07-Aug-2025
 ************************************************************/

class Node {
    int data;
    Node left;
    Node right;
    Node(int data) {
        this.data = data;
        left= null;
        right=null;
    }
}

public class BinarySearchTree {
    Node root;

    public BinarySearchTree() {
        root = null;
    }

    // insert a value into BST
    public void insert(int data) {
        Node newData = new Node(data);
        Node curr, parent;

        if(root== null) {
            root = newData;
        } else {
            curr = root;
            while (true) {
                parent = curr;
                if(data < parent.data) {
                    curr = curr.left;
                    if(curr == null) {
                        parent.left = newData;
                        return;
                    }
                } else {
                    curr = curr.right;
                    if(curr==null) {
                        parent.right = newData;
                        return;
                    }
                }
            }
        }
    }

    // search a value in BST
    public Node search(int data) {
        Node curr = root;
        while(curr.data!=data) {
            if(curr != null) {
                if(curr.data > data) {
                    curr = curr.left;
                }else {
                    curr = curr.right;
                }
                if(curr == null) {
                    return null;
                }
            }
        }
        return curr;
    }

    // DFS
    // Inorder traversal
    public void inOrderTraversal(){
        inOrderRec(root);
    }
    private void inOrderRec(Node root) {
        if(root == null) return;
        inOrderRec(root.left);
        System.out.print(root.data+" ");
        inOrderRec(root.right);
    }

    // pre order traversal
    public void preOrderTraversal(){
        preOrderRec(root);
    }
    private void preOrderRec(Node root) {
        if (root==null) return;
        System.out.print(root.data+" ");
        preOrderRec(root.left);
        preOrderRec(root.right);
    }

    // post order traversal
    public void postOrderTraversal(){
        postOrderRec(root);
    }
    private void postOrderRec(Node root) {
        if (root==null) return;
        postOrderRec(root.left);
        postOrderRec(root.right);
        System.out.print(root.data+" ");
    }

    // level order traversal (BFS)
    public void levelOrderTraversal() {
        int h = height(root);
        for(int i=1;i<=h;i++) {
            traverseLevel(root, i);
        }
    }
    private void traverseLevel(Node root, int level) {
        if(root==null) return;
        if(level==1){
            System.out.print(root.data+" ");
        } else {
            traverseLevel(root.left, level-1);
            traverseLevel(root.right, level-1);
        }
    }

    // tree height
    public int height(){
        return height(root);
    }
    private int height(Node root) {
        if(root == null) return 0;
        return 1+Math.max(height(root.left), height(root.right));
    }

    // delete recursive approach
    public Node deleteRecursive(int data) {
        return delete(root, data);
    }
    // delete iterative approach
     public void delete(int data) {

        if(root == null) return;

        Node curr = root;
        Node parent = null;

        // find node
        while(curr != null && curr.data != data) {
            parent = curr;
            if(data < curr.data) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        // case 1 - remove with one child or no child
        if(curr.left == null || curr.right == null) {
            Node child = curr.left != null ? curr.left : curr.right;
            if(parent==null) {
                root = child;
            } else if(parent.left == curr) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }
        // case 2 - remove with two child
        else {
            Node succParent = curr;
            Node succ = curr.right;
            while (succ.left != null) {
                succParent = succ;
                succ = succ.left;
            }
            curr.data = succ.data;
            if(succParent.left == succ) {
                succParent.left = succ.right;
            } else {
                succParent.right = succ.right;
            }
        }
    }

    // delete recursivly
    private Node delete(Node root, int data) {
        if(root==null) return null;

        if(data<root.data) {
            root.left = delete(root.left, data);
        }else if(data > root.data) {
            root.right = delete(root.right, data);
        } else {
            // 1.Node with only one child or no child
            if(root.left == null) {
                return root.right;
            } else if(root.right == null) {
                return root.left;
            }

            // 2. Node with two children - Get inorder successor (smallest in right)
            root.data = minValue(root.right);
            // delete the inorder successor
            root.right = delete(root.right, root.data);
        }
        return root;
    }

    // find min value
    public int minValue(){
        return minValue(root);
    }
    private int minValue(Node root) {
        while (root.left!= null) {
            root = root.left;
        }
        return root.data;
    }
    // find max value
    public int maxValue(){
        return maxValue(root);
    }
    private int maxValue(Node root) {
        while (root.right!= null) {
            root = root.right;
        }
        return root.data;
    }

    // count total nodes
    public int countNodes(){
        return countNodes(root);
    }
    private int countNodes(Node root) {
        if(root == null) return 0;
        return 1+countNodes(root.left) + countNodes(root.right);
    }

    // count leaf nodes
    public int countLeaves() {
        return countLeaves(root);
    }
    private int countLeaves(Node root) {
        if(root == null) return 0;
        if(root.left == null && root.right == null) return 1;
        return countLeaves(root.left) + countLeaves(root.right);
    }

    // Check if the binary tree is a valid BST
    /**
     * 1. All values in the left subtree are less than the current node.
     * 2. All values in the right subtree are greater than the current node.
     */

    public boolean isBST() {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private boolean isBST(Node root, int min, int max) {
        if(root == null) {
            return true;
        }

        if(root.data<=min || root.data>=max){
            return false;
        }
        return isBST(root.left, min, root.data) && isBST(root.right, root.data, max);
    }

    // Check if the tree is height-balanced

    /**
     * For every node, the height difference between left and right subtrees is at most 1.
     */
    public boolean isBalanced() {
        return isBalanced(root) != -1;
    }
    private int isBalanced(Node root) {
        if(root == null) {
            return 0;
        }
        int leftHeight = isBalanced(root.left);
        if(leftHeight == -1) {
            return -1;
        }

        int rightHeight = isBalanced(root.right);
        if(rightHeight == -1) {
            return -1;
        }
        if(Math.abs(leftHeight-rightHeight)>1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight)+1;
    }

    // check  binary tree is complete or not
    /**
     * All levels are completely filled except possibly the last,
     * and all nodes of the last level are as far left as possible.
     */
    public boolean isComplete() {
        int totalNodes = countNodes();
        return isComplete(root, 0, totalNodes);
    }
    private boolean isComplete(Node root, int index, int totalNodes) {
        if(root==null) {
            return true;
        }
        if(index>=totalNodes) {
            return false;
        }
        boolean left = isComplete(root.left, 2*index+1, totalNodes);
        boolean right = isComplete(root.right, 2*index+2, totalNodes);

        return left && right;
    }

    // check binary tree is perfect or not
    /**
     * All internal nodes have exactly 2 children
     * All leaf nodes are at the same depth
     */
    public boolean isPerfect() {
        int depth = findDepth(root);
        return isPerfect(root, depth, 0);
    }
    private boolean isPerfect(Node root, int depth, int level) {
        if(root==null) {
            return true;
        }
        // check for leaf node
        if(root.left == null && root.right == null) {
            return depth == level+1;
        }
        // if one child is missing
        if(root.left == null || root.right == null) {
            return false;
        }

        return isPerfect(root.left, depth, level+1) && isPerfect(root.right, depth, level+1);
    }
    // get depth of leftmost leaf
    private int findDepth(Node root) {
        int depth =0;
        while (root!=null) {
            depth++;
            root = root.left;
        }
        return depth;
    }

    // check binary tree is full
    /**
     * Every node has either 0 or 2 children.
     * No node has only one child.
     */
    public boolean isFull() {
        return  isFull(root);
    }
    private boolean isFull(Node root) {
        if(root == null) {
            return true;
        }
        // leaf node
        if(root.left == null && root.right == null) {
            return true;
        }
        // node with both children
        if(root.left != null && root.right != null) {
            return isFull(root.left) && isFull(root.right);
        }
        // node with only one child
        return false;
    }

    public static void main(String[] args) {
        // 🔸 CODED BY MASUM ✨ | NEVER STOP LEARNING 🚀

        BinarySearchTree tree = new BinarySearchTree();
        int[] values = {10, 5, 15, 3, 7, 12, 17};
        for (int val : values) {
            tree.insert(val);
        }

        System.out.print("In-order Traversal: ");
        tree.inOrderTraversal();

        System.out.print("\nPre-order Traversal: ");
        tree.preOrderTraversal();

        System.out.print("\nPost-order Traversal: ");
        tree.postOrderTraversal();

        System.out.print("\nLevel-order Traversal: ");
        tree.levelOrderTraversal();

        Node ifFind = tree.search(12);
        if(ifFind!=null) {
            System.out.println("\nItem is found: " + ifFind.data);
        } else  {
            System.out.println("\nItem is not found.");
        }

        System.out.println("Min value: " + tree.minValue());
        System.out.println("Max value: " + tree.maxValue());
        System.out.println("Tree Height: " + tree.height());
        System.out.println("Total Nodes: " + tree.countNodes());
        System.out.println("Leaf Nodes: " + tree.countLeaves());

        tree.delete(15);
        System.out.print("After deleting 15, In-order: ");
        tree.inOrderTraversal();

        tree.delete(3);
        System.out.print("\nAfter deleting root node 3, In-order: ");
        tree.inOrderTraversal();

        tree.delete(17);
        System.out.print("\nAfter deleting no child node 17, In-order: ");
        tree.inOrderTraversal();

        System.out.println("\nIs valid BST: " + tree.isBST());

        System.out.println("Is Balanced: " + tree.isBalanced());

        System.out.println("Is Complete: " + tree.isComplete());

        System.out.println("Is Perfect: " + tree.isPerfect());

        System.out.println("Is Full: " + tree.isFull());
        tree.insert(30);
        System.out.println("Is Full after inserting 3: " + tree.isFull());

    }

}
/************************************************************
 * 🔚 End of Tree.java
 * 📣 Built for mastering DSA concepts step by step
 * ✍️ Author: Masum | Keep coding, keep learning
 ************************************************************/

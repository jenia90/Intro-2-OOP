package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the AVL tree data structure.
 */
public class AvlTree implements Iterable<Integer> {

    public static final int ERROR_VAL = -1;

    private int size = 0;
    private AvlTreeNode rootNode;

    /**
     * Default constructor
     */
    public AvlTree(){
        rootNode = null;
    }

    /**
     * A copy constructor that creates a deep copy of the given AvlTree.
     * @param tree The AVL tree to be copied.
     * @throws NullPointerException
     */
    public AvlTree(AvlTree tree) throws NullPointerException{
        this(tree.inOrderTraversal(tree.rootNode, new int[tree.size()], 0));
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     * @param data the values to add to tree.
     * @throws NullPointerException
     */
    public AvlTree(int[] data) throws NullPointerException{
        this();

        if (data == null) throw new NullPointerException();

        try {
            for (int val : data) {
                add(val);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

    /**
     * Sets the rootNode of the tree
     * @param newRoot the new rootNode.
     */
    protected void setRoot(AvlTreeNode newRoot){
        rootNode = newRoot;
    }

    /**
     * Add a new node with the given value to the tree.
     * @param newValue the value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added, false otherwise.
     */
    public boolean add(int newValue){
        if (findNode(newValue) != null)
            return false;

        if(rootNode == null){
            setRoot(new AvlTreeNode(newValue, this, null));
        } else {
            addTo(rootNode, newValue);
        }

        size++;
        return true;
    }

    /**
     * Recursive helper method for the add method.
     * @param node the node that the new value node should be added to.
     * @param value the value to be added
     */
    private void addTo(AvlTreeNode node, int value){
        if(value < node.getValue()){
            AvlTreeNode left = node.getLeftChildNode();

            if(left == null){
                node.setLeftChildNode(new AvlTreeNode(value, this, node));
            } else {
                addTo(left,value);
            }
        } else {
            AvlTreeNode right = node.getRightChildNode();

            if(right == null){
                node.setRightChildNode(new AvlTreeNode(value, this, node));
            } else {
                addTo(right, value);
            }
        }

        node.balanceNode();
    }

    /**
     * Check whether the tree contains the given input value.
     * @param searchVal value to search for
     * @return if val is found in the tree, return the depth of the node (0 for the root) with the given value
     * if it was found in the tree, -1 otherwise.
     */
    public int contains(int searchVal){
        // first we find the node in question.
        AvlTreeNode node = findNode(searchVal);
        int height = 0;


        if(node != null) {
            AvlTreeNode currentNode = rootNode;

            // if it exists we take the path to it and increment the height variable with each step
            while(currentNode != null){
                if(searchVal < currentNode.getValue()){
                    currentNode = currentNode.getLeftChildNode();
                    height++;
                } else if (searchVal > currentNode.getValue()){
                    currentNode = currentNode.getRightChildNode();
                    height++;
                } else break;
            }

            return height;
        }

        return ERROR_VAL;
    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete the value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public boolean delete(int toDelete){
        // get the node in question
        AvlTreeNode currentNode = findNode(toDelete);

        // if it's null (doesn't exist), return false.
        if(currentNode == null)
            return false;

        // get the parent and the left and right children of the node.
        AvlTreeNode treeToBalance = currentNode.getParentNode();
        AvlTreeNode left = currentNode.getLeftChildNode();
        AvlTreeNode right = currentNode.getRightChildNode();
        int value = currentNode.getValue();

        // decrement the size.
        size--;

        // Case 1: If currentNode has no right child, then current's left replaces current.
        if (right == null){
            updateTree(treeToBalance, left, value);
        // Case 2: If current's right child has no left child, then current's right child
        //         replaces current
        } else if (right.getLeftChildNode() == null) {
            right.setLeftChildNode(left);

            updateTree(treeToBalance, right, value);
        }
        // Case 3: If current's right child has a left child, replace current with current's
        //         right child's left-most child
        else {
            AvlTreeNode leftMost = right.getLeftChildNode();

            while (leftMost.getLeftChildNode() != null){
                leftMost = leftMost.getLeftChildNode();
            }

            // the parent's left subtree becomes the leftmost's right subtree
            leftMost.getParentNode().setLeftChildNode(leftMost.getRightChildNode());

            // assign leftmost's left and right to current's left and right children
            leftMost.setLeftChildNode(left);
            leftMost.setRightChildNode(right);

            updateTree(treeToBalance, leftMost, value);
        }

        // if the parent node isn't null we balance the tree.
        if (treeToBalance != null)
            treeToBalance.balanceNode();
        // else we balance the tree from the root.
        else {
            if (rootNode != null)
                rootNode.balanceNode();
        }

        return true;
    }

    /**
     * Updates the parent node after the removal of the unwanted node.
     * @param parentNode the parent of our node.
     * @param childNode on of the children of our node
     * @param value the value of our node
     */
    private void updateTree(AvlTreeNode parentNode, AvlTreeNode childNode, int value) {
        // if our node was the root, we replace the root with the given childNode.
        if (parentNode == null){
            rootNode = childNode;
            // if the tree isn't empty after the assignment we update the parent property of the new root.
            if (rootNode != null)
                rootNode.setParentNode(null);

        } else {
            // if parent value is greater than current value
            // make the current node child a left child of parent
            if (value < parentNode.getValue())
                parentNode.setLeftChildNode(childNode);

            // if parent value is less than current value
            // make the current node child a right child of parent
            else if (value > parentNode.getValue())
                parentNode.setRightChildNode(childNode);
        }
    }

    /**
     * Gets the node with the given value.
     * @param value value to search for in the tree.
     * @return returns the node with the given value.
     */
    private AvlTreeNode findNode(int value){
        AvlTreeNode currentNode = rootNode;

        while(currentNode != null){
            // if currentNode value is greater than the value we are looking for, we go left in the tree.
            if(value < currentNode.getValue()){
                currentNode = currentNode.getLeftChildNode();
            // if it is greater then we go right.
            } else if (value > currentNode.getValue()){
                currentNode = currentNode.getRightChildNode();
            // if equal we exit the loop and return currentNode.
            } else break;
        }

        return currentNode;
    }

    /**
     * This method converts the tree structure into a sorted int array.
     * @param node node in the tree to travers from.
     * @param currentList current int[] array that we are going to add items to
     * @param index current index in the array.
     * @return int[] array with all the children of a given node in ascending order.
     */
    protected int[] inOrderTraversal(AvlTreeNode node, int[] currentList, int index){
        if (node != null){
            // first go over the left nodes
            inOrderTraversal(node.getLeftChildNode(), currentList, index);
            // add the current value to the array
            currentList[++index] = node.getValue();
            // go to the right
            inOrderTraversal(node.getRightChildNode(), currentList, index);
        }

        return currentList;
    }

    /**
     * Returns the number of nodes in the tree (its size).
     * @return the number of nodes in the tree.
     */
    public int size(){
        return size;
    }

    /**
     * Calculates the minimum number of nodes in an AVL tree of height h.
     * @param h the height of the tree (a non-negative number) in question.
     * @return the minimum number of nodes in an AVL tree of the given height.
     */
    public static int findMinNodes(int h){
        double sqrt5 = Math.sqrt(5);
        return (int) (((2 + sqrt5) * Math.pow(1 + sqrt5, h) + (sqrt5 - 2) * Math.pow(1 - sqrt5, h))
                / (sqrt5 * Math.pow(2, h)) - 1);
    }

    /**
     * Returns an iterator over elements of type int.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        // create a list of all elements in the tree
        int[] valueList = inOrderTraversal(rootNode, new int[size], 0);

        Iterator<Integer> it = new Iterator<Integer>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public Integer next() throws NoSuchElementException {
                if(!hasNext()) throw new NoSuchElementException();

                return valueList[currentIndex++];
            }
        };

        return it;
    }
}

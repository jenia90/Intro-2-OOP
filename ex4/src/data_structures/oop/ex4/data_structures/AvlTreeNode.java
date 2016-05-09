package oop.ex4.data_structures;
/**
 * This class represents a single node in the AvlTree data structure.
 */
public class AvlTreeNode {
    // an enum of all different tree states
    private enum TreeState{
        Balanced,
        LeftHeavy,
        RightHeavy
    }

    /**
     * Constants
     */
    public static final int ERROR_VAL = -1;
    public static final int MAX_LEFT_BALANCE_FACTOR = -1;
    public static final int MIN_RIGHT_BALANCE_FACTOR = 1;

    /**
     * Member variable of the node class
     */
    private AvlTree tree;
    private AvlTreeNode parentNode;
    private AvlTreeNode rightChildNode;
    private AvlTreeNode leftChildNode;
    private int value;

    /**
     * AvlTreeNode constructor
     * @param value the value of this node.
     * @param tree the tree this node belongs to.
     * @param parentNode the parent node of this node.
     */
    public AvlTreeNode(int value, AvlTree tree, AvlTreeNode parentNode){
        this.value = value;
        this.tree = tree;
        this.parentNode = parentNode;
    }

    /**
     * @return the parent node
     */
    public AvlTreeNode getParentNode(){ return parentNode;}

    /**
     * @return the right child node.
     */
    public AvlTreeNode getRightChildNode(){
        return rightChildNode;
    }

    /**
     * @return the left child node.
     */
    public AvlTreeNode getLeftChildNode(){
        return leftChildNode;
    }

    /**
     * @return the value of the node.
     */
    public int getValue(){
        return value;
    }

    /**
     * Sets the parent node of this node.
     * @param parentNode the parent node to assign.
     */
    public void setParentNode(AvlTreeNode parentNode){
        this.parentNode = parentNode;
    }

    /**
     * Sets the right child node of this node.
     * @param node the node to be assigned on the right.
     */
    public void setRightChildNode(AvlTreeNode node){
        rightChildNode = node;

        // if the child node is not null we update it's parent to be this node.
        if(node != null){
            rightChildNode.setParentNode(this);
        }
    }

    /**
     * Sets the right child node of this node.
     * @param node the node to be assigned on the right.
     */
    public void setLeftChildNode(AvlTreeNode node){
        leftChildNode = node;

        // if the child node is not null we update it's parent to be this node.
        if(node != null){
            leftChildNode.setParentNode(this);
        }
    }

    /**
     * Sets the value of this node
     * @param value the new value to assign.
     */
    public void setValue(int value){
        this.value = value;
    }

    /**
     * Checks the state of this node's subtree and balances it accordingly
     */
    public void balanceNode(){
        if(getState() == TreeState.RightHeavy) {
            if (rightChildNode != null && rightChildNode.balanceFactor() < 0) {
                // if right child is not null and it's left heavy we rotate right and then left.
                rotateLeftRight();
            } else {
                rotateLeft();
            }
        } else if (getState() == TreeState.LeftHeavy){
            if(leftChildNode != null && leftChildNode.balanceFactor() > 0){
                // if the left child is not null and it's right heavy we rotate left and the right.
                rotateRightLeft();
            } else {
                rotateRight();
            }
        }
    }

    /**
     * Rotate the node left.
     */
    private void rotateLeft(){
        //     a
        //      \
        //       b
        //        \
        //         c
        //
        // becomes
        //       b
        //      / \
        //     a   c

        AvlTreeNode newRoot = getRightChildNode();
        // replace the current root with the new root
        replaceRoot(newRoot);
        // take ownership of right's left child as right (now parent)
        setRightChildNode(newRoot.getLeftChildNode());
        // the new root takes this as it's left
        newRoot.setLeftChildNode(this);
    }

    /**
     * Rotate the node right.
     */
    private void rotateRight(){
        //     c (this)
        //    /
        //   b
        //  /
        // a
        //
        // becomes
        //       b
        //      / \
        //     a   c

        AvlTreeNode newRoot = getLeftChildNode();
        // replace the current root with the new root
        replaceRoot(newRoot);
        // take ownership of left's right child as left (now parent)
        setLeftChildNode(newRoot.getRightChildNode());
        // the new root takes this as it's right
        newRoot.setRightChildNode(this);
    }

    /**
     * Rotates the right subtree right and then rotates the node left.
     */
    private void rotateLeftRight(){
        rightChildNode.rotateRight();
        rotateLeft();
    }

    /**
     * Rotates the left subtree left and then rotates the node right.
     */
    private void rotateRightLeft(){
        leftChildNode.rotateLeft();
        rotateRight();
    }

    /**
     * Replaces the root of the tree/subtree.
     * @param newRoot
     */
    private void replaceRoot(AvlTreeNode newRoot){
        if (parentNode != null){
            // if this node has a parent then we check in which subtree this node belongs to and replace the
            // child of the parent with the new node.
            if (parentNode.getLeftChildNode() == this)
                parentNode.setLeftChildNode(newRoot);
            else if (parentNode.getRightChildNode() == this)
                parentNode.setRightChildNode(newRoot);
        } else {
            // if this node is the root of the tree, we replace the root of the tree
            tree.setRoot(newRoot);
        }

        // update the parents of this node and the new root's
        newRoot.setParentNode(parentNode);
        this.setParentNode(newRoot);
    }

    /**
     * Returns the maximum height of this node.
     * @return the height of this node from the farthest leaf.
     */
    public int height(){
        return maxChildHeight(this);
    }

    /**
     * Recursive method to calculate the height of the given node and choose the maximum out of its left and right
     * subtrees.
     * @param node the node to calculate the height for.
     * @return height of the node if it exists; -1 otherwise.
     */
    public int maxChildHeight(AvlTreeNode node){
        if(node != null){
            return 1 + Math.max(maxChildHeight(node.getLeftChildNode()), maxChildHeight(node.getRightChildNode()));
        }

        return ERROR_VAL;
    }

    /**
     * Calculates the height of the left subtree
     * @return the height of the left subtree.
     */
    private int leftHeight(){
        return maxChildHeight(leftChildNode);
    }

    /**
     * Calculates the height of the right subtree.
     * @return the height of the right subtree.
     */
    private int rightHeight(){
        return maxChildHeight(rightChildNode);
    }

    /**
     * Checks the balancing state of the tree
     * @return
     */
    private TreeState getState(){
        if(balanceFactor() > MIN_RIGHT_BALANCE_FACTOR)
            return TreeState.RightHeavy;

        else if(balanceFactor() < MAX_LEFT_BALANCE_FACTOR)
            return TreeState.LeftHeavy;

        return TreeState.Balanced;
    }

    /**
     * Calculates the balance factor of this node.
     * @return
     */
    private int balanceFactor(){
        return rightHeight() - leftHeight();
    }
}
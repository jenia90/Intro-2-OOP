package oop.ex4.data_structures;
/**
 * Created by jenia on 14/04/2016.
 */
public class AvlTreeNode {

    private enum TreeState{
        Balanced,
        LeftHeavy,
        RightHeavy
    }

    private AvlTree tree;
    private AvlTreeNode parentNode;
    private AvlTreeNode rightChildNode;
    private AvlTreeNode leftChildNode;
    private int value;

    public AvlTreeNode(int value, AvlTree tree, AvlTreeNode parentNode){
        this.value = value;
        this.tree = tree;
        this.parentNode = parentNode;
    }

    public AvlTreeNode getParentNode(){ return parentNode;}

    public AvlTreeNode getRightChildNode(){
        return rightChildNode;
    }

    public AvlTreeNode getLeftChildNode(){
        return leftChildNode;
    }

    public int getValue(){
        return value;
    }

    public void setParentNode(AvlTreeNode parentNode){
        this.parentNode = parentNode;
    }

    public void setRightChildNode(AvlTreeNode node){
        rightChildNode = node;

        if(node != null){
            rightChildNode.setParentNode(this);
        }
    }

    public void setLeftChildNode(AvlTreeNode node){
        leftChildNode = node;

        if(node != null){
            leftChildNode.setParentNode(this);
        }
    }

    public void setValue(int value){
        this.value = value;
    }

    protected void balanceNode(){
        if(getState() == TreeState.RightHeavy){
            if(rightChildNode != null && rightChildNode.balanceFactor() < 0){
                rotateLeftRight();
            } else {
                rotateLeft();
            }
        } else if (getState() == TreeState.LeftHeavy){
            if(leftChildNode != null && leftChildNode.balanceFactor() > 0){
                rotateRightLeft();
            } else {
                rotateRight();
            }
        }
    }

    private void rotateLeft(){
        AvlTreeNode newRoot = rightChildNode;
        replaceRoot(newRoot);
        rightChildNode = newRoot.leftChildNode;
        newRoot.leftChildNode = this;
    }

    private void rotateRight(){
        AvlTreeNode newRoot = leftChildNode;
        replaceRoot(newRoot);
        leftChildNode = newRoot.rightChildNode;
        newRoot.rightChildNode = this;
    }

    private void rotateLeftRight(){
        rightChildNode.rotateRight();
        rotateLeft();
    }

    private void rotateRightLeft(){
        leftChildNode.rotateLeft();
        rotateRight();
    }

    private void replaceRoot(AvlTreeNode newRoot){
        if (this.parentNode != null){
            if (this.parentNode.leftChildNode == this)
                this.parentNode.leftChildNode = newRoot;
            else if (this.parentNode.rightChildNode == this)
                this.parentNode.rightChildNode = newRoot;
        } else {
            tree.setRoot(newRoot);
        }

        newRoot.setParentNode(this.parentNode);
        this.setParentNode(newRoot);
    }

    private int maxChildHeight(AvlTreeNode node){
        if(node != null){
            return 1 + Math.max(maxChildHeight(node.getLeftChildNode()), maxChildHeight(node.getRightChildNode()));
        }

        return 0;
    }

    private int leftHeight(){
        return maxChildHeight(leftChildNode);
    }

    private int rightHeight(){
        return maxChildHeight(rightChildNode);
    }

    private TreeState getState(){
        if(balanceFactor() > 1)
            return TreeState.RightHeavy;

        else if(balanceFactor() < 1)
            return TreeState.LeftHeavy;

        return TreeState.Balanced;
    }

    private int balanceFactor(){
        return rightHeight() - leftHeight();
    }
}
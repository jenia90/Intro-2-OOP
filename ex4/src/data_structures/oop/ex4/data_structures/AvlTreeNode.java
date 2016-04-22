package oop.ex4.data_structures;
/**
 * Created by jenia on 14/04/2016.
 */
public class AvlTreeNode {
    private AvlTree tree;
    private AvlTreeNode parentNode;
    private AvlTreeNode rightChildNode;
    private AvlTreeNode leftChildNode;
    private int value;

    public AvlTreeNode(){
        this(0, new AvlTree(), null);
    }

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
        if(state() == TreeState.RightHeavy){
            if(rightChildNode != null && rightChildNode.balanceFactor() < 0){
                rotateLeftRight();
            } else {
                rotateLeft();
            }
        } else if (state() == TreeState.LeftHeavy){
            if(leftChildNode != null && leftChildNode.balanceFactor() > 0){
                rotateRightLeft();
            } else {
                rotateRight();
            }
        }
    }

    public void rotateLeft(){
        parentNode.setRightChildNode(leftChildNode);
        setLeftChildNode(parentNode);
    }

    public void rotateRight(){
        parentNode.setLeftChildNode(rightChildNode);
        setRightChildNode(parentNode);
    }

    public void rotateLeftRight(){
        rightChildNode.rotateRight();
        rotateLeft();
    }

    public void rotateRightLeft(){
        leftChildNode.rotateLeft();
        rotateRight();
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

    private TreeState state(){
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

enum TreeState{
    Balanced,
    LeftHeavy,
    RightHeavy
}

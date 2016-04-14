/**
 * Created by jenia on 14/04/2016.
 */
public class Node extends AvlTree {
    private AvlTree rightChild;
    private AvlTree leftChild;
    private int data;

    public AvlTree getRightChild(){
        return rightChild;
    }

    public AvlTree getLeftChild(){
        return leftChild;
    }

    public void setRightChild(AvlTree node){
        rightChild = node;
    }

    public void setLeftChild(AvlTree node){
        leftChild = node;
    }
}

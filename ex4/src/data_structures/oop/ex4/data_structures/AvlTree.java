package oop.ex4.data_structures;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jenia on 08/04/2016.
 */
public class AvlTree implements Iterable<Integer> {

    public static final int ERROR_VAL = -1;

    private int size = 0;
    private AvlTreeNode rootNode;

    public AvlTree(){
        rootNode = null;
    }

    public AvlTree(AvlTree avlTree){
        this();

        rootNode = new AvlTreeNode(avlTree.getRoot().getValue(), avlTree, null);
        size = avlTree.size();
    }

    public AvlTree(int[] data){
        this();

        try {
            for (int val :
                    data) {
                add(val);
            }
        } catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }

    }

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

    protected AvlTreeNode getRoot(){
        return rootNode;
    }

    protected void setRoot(AvlTreeNode newRoot){
        rootNode = newRoot;
    }

    public int contains(int searchVal){
        AvlTreeNode node = findNode(searchVal);
        int height = 0;

        if(node != null) {
            FromFilesTester.printTree();
            AvlTreeNode currentNode = rootNode;


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

    public boolean delete(int toDelete){
        AvlTreeNode currentNode;
        currentNode = findNode(toDelete);

        if(currentNode == null)
            return false;

        AvlTreeNode treeToBalance = currentNode.getParentNode();
        AvlTreeNode left = currentNode.getLeftChildNode();
        AvlTreeNode right = currentNode.getRightChildNode();
        int value = currentNode.getValue();
        size--;

        // Case 1: If currentNode has no right child, then current's left replaces current.
        if (right == null){
            if (treeToBalance == null){
                rootNode = left;

                if (rootNode != null)
                    rootNode.setParentNode(null);
            } else {
                // if parent value is greater than current value
                // make the current left child a left child of parent
                if (value < treeToBalance.getValue())
                    treeToBalance.setLeftChildNode(left);

                // if parent value is less than current value
                // make the current left child a right child of parent
                else if (value > treeToBalance.getValue())
                    treeToBalance.setRightChildNode(left);
            }
        // Case 2: If current's right child has no left child, then current's right child
        //         replaces current
        } else if (right.getLeftChildNode() == null) {
            right.setLeftChildNode(left);

            if (treeToBalance == null){
                rootNode = right;

                if (rootNode != null)
                    rootNode.setParentNode(null);
            } else {
                // if parent value is greater than current value
                // make the current right child a left child of parent
                if (value < treeToBalance.getValue())
                    treeToBalance.setLeftChildNode(right);

                // if parent value is less than current value
                // make the current right child a right child of parent
                else if (value > treeToBalance.getValue())
                    treeToBalance.setRightChildNode(right);
            }
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

            if(treeToBalance == null){
                rootNode = leftMost;

                if(rootNode != null)
                    rootNode.setParentNode(null);
            } else {
                if (value < treeToBalance.getValue())
                    treeToBalance.setLeftChildNode(leftMost);

                else if(value > treeToBalance.getValue())
                    treeToBalance.setRightChildNode(leftMost);
            }
        }

        if (treeToBalance != null)
            treeToBalance.balanceNode();
        else {
            if (rootNode != null)
                rootNode.balanceNode();
        }

        return true;
    }

    private AvlTreeNode findNode(int value){
        AvlTreeNode currentNode = rootNode;
        int height = 0;

        while(currentNode != null){
            if(value < currentNode.getValue()){
                currentNode = currentNode.getLeftChildNode();
                height++;
            } else if (value > currentNode.getValue()){
                currentNode = currentNode.getRightChildNode();
                height++;
            } else break;
        }

        return currentNode;
    }

    private ArrayList<Integer> inOrderTraversal(AvlTreeNode node, ArrayList<Integer> currentList){
        if (node != null){
            inOrderTraversal(node.getLeftChildNode(), currentList);
            currentList.add(node.getValue());
            inOrderTraversal(node.getRightChildNode(), currentList);
        }

        return currentList;
    }

    public int size(){
        return size;
    }

    public static int findMinNodes(int h){
        if(h == 0)
            return 1;
        if(h == 1)
            return 2;

        return findMinNodes(h-1) + findMinNodes(h-2) + 1;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        ArrayList<Integer> valueList = inOrderTraversal(rootNode, new ArrayList<Integer>());

        Iterator<Integer> it = new Iterator<Integer>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < valueList.size();
            }

            @Override
            public Integer next() {
                return valueList.get(currentIndex++);
            }
        };

        return it;
    }
}

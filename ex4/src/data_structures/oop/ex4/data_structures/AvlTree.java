package oop.ex4.data_structures;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jenia on 08/04/2016.
 */
public class AvlTree implements Iterable<Integer> {

    private int size = 0;
    private AvlTreeNode rootNode;

    public AvlTree(){
        rootNode = null;
    }

    public AvlTree(AvlTree avlTree){
        rootNode = new AvlTreeNode(avlTree.getRoot().getValue(), avlTree, null);
        size = avlTree.size();
    }

    public AvlTree(int[] data){
        if(data == null){
            throw new NullPointerException("Empty data constructor used.");
        }
        for (int val :
                data) {
            add(val);
        }
    }

    public boolean add(int newValue){
        if(rootNode == null){
            setRoot(new AvlTreeNode(newValue, new AvlTree(), null));
            size++;
            return true;
        } else {
            if (addTo(rootNode, newValue)){
                size++;
                System.out.println(inOrderTraversal(rootNode, null)); // TODO: REMOVE!
                return true;
            }
        }

        return false;
    }

    private boolean addTo(AvlTreeNode node, int value){
        AvlTreeNode parentNode = node.getParentNode();
        if(value < node.getValue()){
            if(node.getLeftChildNode() == null){
                node.setLeftChildNode(new AvlTreeNode(value, new AvlTree(), node));
            } else{
                addTo(node.getLeftChildNode(),value);
                if(node.getParentNode() != null)
                    node.getParentNode().balanceNode();
            }
            return true;
        } else if (value > node.getValue()){
            if(node.getRightChildNode() == null){
                node.setRightChildNode(new AvlTreeNode(value, new AvlTree(), node));
            } else {
                addTo(node.getRightChildNode(), value);
                if(node.getParentNode() != null)
                    node.getParentNode().balanceNode();
            }

            return true;
        }

        return false;
    }

    protected AvlTreeNode getRoot(){
        return rootNode;
    }

    protected void setRoot(AvlTreeNode newRoot){
        rootNode = newRoot;
    }

    public int contains(int searchVal){
        AvlTreeNode node = findNode(searchVal);

        if(node != null) {
            if (node == rootNode)
                return 0;
            return rootNode.maxChildHeight(node) - 1;
        }

        return -1;
    }

    public boolean delete(int toDelete){
        AvlTreeNode currentNode = findNode(toDelete);

        if(currentNode == null)
            return false;

        AvlTreeNode treeToBalance = currentNode.getParentNode();
        size--;

        if(currentNode.getRightChildNode() == null){
            if (currentNode.getParentNode() == null){
                rootNode = currentNode.getLeftChildNode();
                if (rootNode != null){
                    rootNode.setParentNode(null);
                }
            } else {
                if (currentNode.getParentNode().getValue() > currentNode.getValue())
                    currentNode.getParentNode().setLeftChildNode(currentNode.getLeftChildNode());
                else if (currentNode.getParentNode().getValue() < currentNode.getValue())
                    currentNode.getParentNode().setRightChildNode(currentNode.getLeftChildNode());
            }
        } else if (currentNode.getRightChildNode().getLeftChildNode() == null){
            currentNode.getRightChildNode().setLeftChildNode(currentNode.getLeftChildNode());

            if (currentNode.getParentNode() == null){
                rootNode = currentNode.getRightChildNode();
                if (rootNode != null){
                    rootNode.setParentNode(null);
                }
            } else {
                if (currentNode.getParentNode().getValue() > currentNode.getValue())
                    currentNode.getParentNode().setLeftChildNode(currentNode.getRightChildNode());
                else if (currentNode.getParentNode().getValue() < currentNode.getValue())
                    currentNode.getParentNode().setRightChildNode(currentNode.getRightChildNode());
            }
        } else {
            AvlTreeNode leftMost = currentNode.getRightChildNode().getLeftChildNode();

            while (leftMost.getLeftChildNode() != null){
                leftMost = leftMost.getLeftChildNode();
            }

            leftMost.getParentNode().setLeftChildNode(leftMost.getRightChildNode());

            leftMost.setLeftChildNode(currentNode.getLeftChildNode());

            leftMost.setRightChildNode(currentNode.getRightChildNode());

            if (currentNode.getParentNode() == null){
                rootNode = leftMost;
                if (rootNode != null){
                    rootNode.setParentNode(null);
                }
            } else {
                if (currentNode.getParentNode().getValue() > currentNode.getValue())
                    currentNode.getParentNode().setLeftChildNode(leftMost);
                else if (currentNode.getParentNode().getValue() < currentNode.getValue())
                    currentNode.getParentNode().setRightChildNode(leftMost);
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

        while(currentNode != null){
            if(value < currentNode.getValue()){
                currentNode = currentNode.getLeftChildNode();
            } else if (value > currentNode.getValue()){
                currentNode = currentNode.getRightChildNode();
            } else break;
        }

        return currentNode;
    }

    private ArrayList<Integer> inOrderTraversal(AvlTreeNode node, ArrayList<Integer> currentList){
        if(currentList == null){
            currentList = new ArrayList<Integer>();
        }

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
        ArrayList<Integer> valueList = inOrderTraversal(rootNode, null);

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

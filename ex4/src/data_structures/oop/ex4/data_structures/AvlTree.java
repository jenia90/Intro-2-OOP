package oop.ex4.data_structures;

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
    }

    public AvlTree(int[] data){
        for (int val :
                data) {
            add(val);
        }
    }

    public boolean add(int newValue){
        if(rootNode == null){
            rootNode = new AvlTreeNode(newValue, this, null);
            size++;
            return true;
        } else {
            if (addTo(rootNode, newValue)){
                size++;
                return true;
            }
        }

        return false;
    }

    private boolean addTo(AvlTreeNode node, int value){
        if(value < node.getValue()){
            if(node.getLeftChildNode() == null){
                node.setLeftChildNode(new AvlTreeNode(value, null, node));
            } else{
                addTo(node.getLeftChildNode(),value);
            }
        } else if (value >= node.getValue()){
            if(node.getRightChildNode() == null){
                node.setRightChildNode(new AvlTreeNode(value, null, node));
            } else {
                addTo(node.getRightChildNode(), value);
            }
        }

        return false;
    }

    protected AvlTreeNode getRoot(){
        return rootNode;
    }

    protected void setRoot(AvlTreeNode newRoot){
        rootNode = newRoot;
    }

    public boolean contains(int searchVal){
        return findNode(searchVal) != null; // TODO: redo this.
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

    private int[] inOrderTraversal(AvlTreeNode node, int[] currentList, int index){
        if (node != null){
            inOrderTraversal(node.getLeftChildNode(), currentList, index);
            currentList[index++] = node.getValue();
            inOrderTraversal(node.getRightChildNode(), currentList, index);
        }

        return currentList;
    }

    public int size(){
        return size;
    }

    public static int findMinNodes(int h){
        return 0;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        int[] valueList = inOrderTraversal(rootNode, new int[size], 0);

        Iterator<Integer> it = new Iterator<Integer>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public Integer next() {
                return valueList[currentIndex++];
            }
        };

        return it;
    }
}

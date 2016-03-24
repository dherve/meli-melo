package com.melimelo.bst;

/**
 * Binary search tree. Does not allow duplicate values.
 * #TODO : add height and
 *         derive from this a threaded binary tree i.e
 *         left child that are null need to point to the parent predecessor
 *         right child that are null point to the parent successor
 *         the min has no predecessor
 *         the max has no successor
 *         store are nodes by key
 *         do inorder by key 
 *         for each key in the ordered order 
 *             get the node object by the key 
 *             if the node has a left child null and is the key is not the first
 *                 get the predecessor key
 *                 get the predecessor node
 *                 and set the left child to point ot the predecessor
 *             if the node has a right child null and the key is not the last
 *                 get the successor key
 *                 get the successor node
 *                 and set the right child to point ot the successor
 *          How to test
 *          What's the complexity (memory and speed) ?
 */
public class BinarySearchTree<T extends Comparable<T>> {

    public static int RECURSIVE_STRATEGY = 1;
    public static int ITERATIVE_STRATEGY = 2;
    private final double LOG_2 = Math.log((double)2);
    private Node<T> m_root;
    private int m_size;

    /**
     * Create a new instance.
     */
    public BinarySearchTree() {
    }

    /**
     * Create a new instance and set the root with a specific value.
     * @param rootValue the value of the root to set.
     */
    public BinarySearchTree(final T rootValue) {
        m_root = new Node<T>(rootValue);
        m_size = 1;
    }

    /**
     * @return the tree's root.
     */
    public Node<T> root() {
        return m_root;
    }

    /**
     * Insert a new value in the three. If the value already exists in the tree
     * then no value will be added.
     * @param  value the value to insert.
     * @return A reference to the node with the value inserted.
     */
    public Node<T> insert(final T value) {
        return insert(value, ITERATIVE_STRATEGY);
    }

    /**
     * Insert a new value in the three.
     * @param  value    The value to insert.
     * @param  strategy The strategy to use for insertion.
     * @return          A reference to the node with the value inserted.
     */
    public Node<T> insert(final T value, final int strategy) {
        if(strategy == RECURSIVE_STRATEGY) {
            return recursiveInsert(m_root, value);
        }

        if(strategy == ITERATIVE_STRATEGY) {
            return iterativeInsert(value);
        }

        throw new IllegalArgumentException("Unknown strategy specified !");
    }

    /**
     * insert an new node using an iterative strategy.
     * @param  value the value to insert.
     * @return A reference to the node with the value inserted.
     */
    private Node<T> iterativeInsert(final T value) {
        Node<T> nodeToInsert = new Node<T>(value);
        if(isEmpty()) {
            m_root = nodeToInsert;
            m_size++;
        }else{
            Node<T> parent = m_root;
            boolean nodeInserted = false;
            while(!nodeInserted) {
                int comparisonResult = parent.value().compareTo(value);

                if(comparisonResult == 0) {
                    return parent;
                }

                if(comparisonResult > 0) {
                    if(parent.left() != null) {
                        parent = parent.left();
                    }else{
                        parent.setLeft(nodeToInsert);
                        nodeInserted = true;
                        m_size ++;
                    }
                }else if(comparisonResult < 0) {
                    if(parent.right() != null) {
                        parent = parent.right();
                    }else{
                        parent.setRight(nodeToInsert);
                        nodeInserted = true;
                        m_size ++;
                    }
                }
            }
        }
        return nodeToInsert;
    }
    
    // #TODO review this
    /*
        insert(node:Node, value:T):Node
            if node == null
                return Node(value)
            if node.value < value
                node.right = insert(node.right, value)
            if node.value > value
                node.left = insert(node.left, value)
            return node.
    */
    /**
     * insert an new node using a recursive strategy.
     * @param  value the value to insert.
     * @return A reference to the node with the value inserted.
     */
    private Node<T> recursiveInsert(final Node<T> node, final T value) {

        if(node == null) {
            Node<T> newNode = new Node<T>(value);
            if(node == m_root) {
                m_size ++;
                m_root = newNode;
            }
            return newNode;
        }

        int comparisonResult = node.value().compareTo(value);

        // the value already exists stop.
        if(comparisonResult == 0) {
            return node;
        }

        // if the value to insert is lower than the insertion node value then 
        // recursively insert into the left child if it exists. 
        // If the left child doesn't exist then create a new one and return it.
        if(comparisonResult > 0) {
            if(node.left() != null) {
                return recursiveInsert(node.left(), value);
            }else{
                Node<T> leftChild = new Node<T>(value);
                node.setLeft(leftChild);
                m_size ++;
                return leftChild;
            }
        }

        // if the value to insert is bigger than the insertion node value then 
        // recursively insert into the right child if it exists. 
        // If the left right doesn't exist then create a new one and return it.
        if(comparisonResult < 0) {
            if(node.right() != null) {
                return recursiveInsert(node.right(), value);
            }else{
                Node<T> rightChild = new Node<T>(value);
                node.setRight(rightChild);
                m_size ++;
                return rightChild;
            }
        }

        // otherwise return the insertion node, because it has the same value
        // as the value ot insert.
        return node;
    }

    /**
     * Search a value in the tree.
     * @param  value the value to search.
     * @return       A node  with the value specified, null if the value is
     *               not present in the tree.
     */
    public Node<T> search(final T value) {
        return search(value, ITERATIVE_STRATEGY);
    }

    /**
     * Search a value in the tree.
     * @param  value    the value to search.
     * @param  strategy the strategy to use for searching. The strategy allowed are
     *                  RECURSIVE_STRATEGY or ITERATIVE_STRATEGY.
     * @return          A node  with the value specified, null if the value is
     *                  not present in the tree.
     */
    public Node<T> search(final T value, final int strategy) {
        if(strategy == RECURSIVE_STRATEGY) {
            return recursiveSearch(m_root, value);
        }

        if(strategy == ITERATIVE_STRATEGY) {
            return iterativeSearch(value);
        }

        throw new IllegalArgumentException("Unknown strategy specified !");
    }

    /**
     * Search recursively a value.
     * @param  value the value to search.
     * @return       A node with the value to search, null if not found.
     */
    private Node<T> iterativeSearch(final T value) {
        Node<T> node = m_root;
        while(node != null) {
            int comparisonResult = node.value().compareTo(value);
            if(comparisonResult == 0) {
                return node;
            }else if(comparisonResult > 0) {
                node = node.left();
            }else if(comparisonResult < 0) {
                node = node.right();
            }
        }
        return node;
    }

    /**
     * Search recursively a value in a sutree with the provide node as root.
     * @param  value the value to search.
     * @param  node  the root node of the subtree.
     * @return       A node with the value to search, null if not found.
     */
    private Node<T>  recursiveSearch(final Node<T> node, final T value) {
        if(node == null) {
            return null;
        }

        int comparisonResult = node.value().compareTo(value); 
        if(comparisonResult == 0) {
            return node;
        }
        if(comparisonResult > 0) {
            return recursiveSearch(node.left(), value);
        }
        return recursiveSearch(node.right(), value);
    }

    /**
     * Verify if the tree contains a value.
     * @param  value the value to check.
     * @return       true if the value is not null and is contained in the tree,
     *               false otherwise.
     */
    public boolean contains(final T value) {
        if(value == null) {
            return false;
        }

        Node<T> node = search(value);
        return node != null && value.compareTo(node.value()) == 0;
    }

    /** 
     * Delete a value from the tree.
     * #TODO : split the function in smaller functions.
     * @param  value the value to delete
     * @return       A node with the value deleted. Null if there's no node
     *               with the value to delete.
     */
    public Node<T> delete(final T value) {
        Node<T> nodeToDelete = search(value);

        if(nodeToDelete == null) {
            return null;
        }

        Node<T> parent = nodeToDelete.parent();
        
        // Delete internal or leaf node
        if (parent != null) {
            
            // Delete leaf node
            if (nodeToDelete.isLeaf()) {
                if (nodeToDelete.isRightChild()) {
                    parent.setRight(null);
                } else if (nodeToDelete.isLeftChild()) {
                    parent.setLeft(null);
                }
                
            // The node to delete has only the left child
            } else if (nodeToDelete.right() == null) {
                
                // replace 
                if (nodeToDelete.isRightChild()) {
                    parent.setRight(nodeToDelete.left());
                } else if (nodeToDelete.isLeftChild()) {
                    parent.setLeft(nodeToDelete.left());
                }
                
            // The node to delete has only the right child
            } else if (nodeToDelete.left() == null) {
                if (nodeToDelete.isRightChild()) {
                    parent.setRight(nodeToDelete.right());
                } else if (nodeToDelete.isLeftChild()) {
                    parent.setLeft(nodeToDelete.right());
                }
            
            // The node to delete has two children
            } else {

                // The successor (i.e the min the right child) will replace him
                Node<T> successor = min(nodeToDelete.right());
                
                // if the succesor is not a direct child of the node to delete
                if (successor.parent() != nodeToDelete) {
                    
                    // then the right child will replace him.
                    Node<T> successorParent = successor.parent();
                    
                    // when the successor is not a direct child of the node
                    // to delete then it must be a left child.
                    // Hence we set the left child here.
                    successorParent.setLeft(successor.right());
                    
                    // 
                    successor.setRight(nodeToDelete.right());
                }
                
                parent.setRight(successor);
                successor.setLeft(nodeToDelete.left());

            }
            nodeToDelete.setParent(null);
        } else {
            // delete the root
            
            // the root has no child then we have nothing to do
            if(nodeToDelete.isLeaf()){
                
                m_root = null;
            
            // the root has only one left child. He will take his place
            } else if (nodeToDelete.right() == null) {
               m_root = nodeToDelete.left();
               m_root.setParent(null);
                
            // the root has only one right child
            } else if (nodeToDelete.left() == null) {
                m_root = nodeToDelete.right();
                m_root.setParent(null);
                
            // the root has two children. the successor will replace him.
            }else {
                Node<T> successor = min(nodeToDelete.right());
                
                // if the successor is not a direct child of the node to delete
                if (successor.parent() != nodeToDelete) {
                    
                    // then the right child will replace him.
                    Node<T> successorParent = successor.parent();
                    
                    // when the successor is not a direct child of the node
                    // to delete then it must be a left child.
                    // Hence we set is as a left child here.
                    successorParent.setLeft(successor.right());
                    successor.setRight(nodeToDelete.right());
                }
                                
                successor.setLeft(nodeToDelete.left());
                m_root = successor;
                m_root.setParent(null);
            }
            
        }
        m_size --;
        return nodeToDelete;
    }

    /**
     * @return The node with the minimum value in the tree.
     */
    public Node<T> min() {

        if(isEmpty()){
            return null;
        }
        return min(m_root);
    }

    /**
     * Search the minimum value in a subtree.
     * @param  root the subtree root node.
     * @return the Node with the minimum value in the subtree.
     */
    private Node<T> min(final Node<T> root) {
        Node<T> node = root;
        while(node.left() != null) {
            node = node.left();
        }
        return node;
    }

    /**
     * @return The node with the maximum value in the tree.
     */
    public Node<T> max() {

        if(isEmpty()) {
            return null;
        }
        return max(m_root);
    }

    /**
     * Search the maximum value in a subtree.
     * @param  root the subtree root node.
     * @return the Node with the maximum value in the subtree.
     */
    private Node<T> max(final Node<T> root) {
        Node<T> node = root;
        while(node.right() != null) {
            node = node.right();
        }
        return node;
    }

    /**
     * Get the successor of a value. Here the successor of a value is the
     * value that come right after the specified value in the order specified
     * by an in-order traversal.
     * @param  value the value for which to get the successor.
     * @return       A node with the successor value.
     */
    public Node<T> successor(final T value) {
        Node<T> node = search(value);
        if(node == null) {
            return null;
        }

        if(node.right() != null) {
            return min(node.right());
        }

        Node<T> successor = node.parent();
        while(successor != null && node == successor.right()) {
            node = successor;
            successor = successor.parent();
        }
        return successor;
    }

    /**
     * Get the predecessor of a value. Here the predecessor of a value is the
     * value that come right before the specified value in the order specified
     * by an in-order traversal.
     * @param  value the value for which to get the predecessor.
     * @return       A node with the predecessor value.
     */
    public Node<T> predecessor(final T value) {
        Node<T> node = search(value);

        if(node == null) {
            return null;
        }

        if(node.left() != null) {
            return max(node.left());
        }

        Node<T> predecessor = node.parent();
        while(predecessor != null && node == predecessor.left()) {
            node = predecessor;
            predecessor = predecessor.parent();
        }
        return predecessor;
    }

    /**
     * @return true it the tree is empty, falste otherwise.
     */
    public boolean isEmpty() {
        return m_root == null;
    }

    /**
     * @return the number elements in the tree.
     */
    public int size() {
        return m_size;
    }

    /**
     * Remove all elements from the tree.
     */
    public void clear() {
        // nlogn =>  (min=> log(n) + delete=>log(n)) * n => n^2 if the tree is alist ?
        // can we do a post order and start by deleting a node that is leaf => O(n) ?
        while(!isEmpty()) {
            delete(min().value());
        }
    }

    private int log2(final int value) {
        return Double.valueOf(Math.log((double) value) / LOG_2).intValue();
    }
}
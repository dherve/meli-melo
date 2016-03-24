package com.melimelo.bst;

/**
 * #TODO add doc and test
 */
public class BstPrettyPrinter <T extends Comparable<T>> {
    private int m_height;
    private int m_width;
    private T [][] m_matrix;
    private BinarySearchTree<T> m_tree;
    private final String EMPTY_SPACE = ".";

    public BstPrettyPrinter(final BinarySearchTree<T> tree) {
        m_tree = tree;
        m_height = (tree.size() * 2) + 1;
        m_width =  tree.size() * 3; // pow(2, tree.size() -1) + 1; 
        m_matrix = (T [][]) new Comparable[m_height][m_width];
    }

    public void print() {
        int delta = m_width / 2;
        place(m_tree.root(), delta, 0, delta);

        // #TODO compute the limit to avoid printing empty row at the bottom
        // also the more we go down the tree the more elements ar to close to
        // each other need to find a way to compute the width so that  those
        // elements do not get too closed to each other
        // 
        // Also when the tree is unbalanced it doesn't work

        for(int i = 0; i < m_height; i ++) {
            System.out.print("[");
            for(int j = 0; j < m_width; j++) {
                if(m_matrix[i][j] != null) {
                    System.out.print(m_matrix[i][j]);
                }else{
                    System.out.print(EMPTY_SPACE);
                }
            }
            System.out.println("]");
        }
    }

    private void expand() {

    }

    /**
     * #TODO fix the algorithm so that all element can be displaye
     * @param delta Horizontal distance between the node and a child
     */
    private void place(final Node<T> node, final int x, final int y, final int delta) {
        if(node == null || positionOutOfBounds(x,y)) {
            return;
        }

        // in 2d arrays the first index is for the line  (y position) 
        // and the second index for the column (x position)
        m_matrix[y][x] = node.value();

        int newDelta = delta / 2;

        // child nodes are 2 row below
        int childrenY = y + 2;

        // the left child is at parent.x - delta, excluding the paren location hence the -1
        place(node.left(), x - 1 - newDelta, childrenY, newDelta);

        // the left child is at parent.x + delta, excluding the paren location hence the +1
        place(node.right(), x + 1 + newDelta, childrenY, newDelta);
    }

    private boolean positionOutOfBounds(final int x, final int y) {
        return  x < 0 || x >= m_width  || y < 0 || y >= m_height;
    }

    private int pow(int value, int power) {
        return (int) Math.pow((double) value, (double) power);
    }

//    private static class GridNode extends Node<T> {
//        private int m_row;
//        private int m_col;
//        public GridNode(final T value, final int row, final int col) {
//            super(value);
//            m_row = row;
//            m_col = col;
//        }
//
//        public int row() {
//            return m_row;
//        }
//
//        public int col() {
//            return m_row;
//        }
//
//        public void setLocation(final int row, final int col) {
//            m_row = row;
//            m_col = col;
//        }
//
//    }

}
package zombicide.board.generator;

import java.util.Random;

/**
 * Code for Leaf class, used for BSP algorithm implementation.
 */
public class Leaf {
    public static final int MIN_LEAF_SIZE = 3;
    public static final int MAX_LEAF_SIZE = 5;

    private int x;
    private int y;
    private int width;
    private int height;

    private Leaf leftChild;
    private Leaf rightChild;

    public Leaf(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Split a leaf in half, in a direction given by method getBestSplitDirection.
     * Side effect : update leafs children
     * @return true if split could be done, false otherwise
     * @see Leaf#getBestSplitDirection()
     */
    public boolean split() {
        boolean splitHorizontally = getBestSplitDirection();

        if (!canSplit(splitHorizontally)) {
            return false;
        }

        Random r = new Random();
        int splitPos = r.nextInt(MIN_LEAF_SIZE, (splitHorizontally ? this.height : this.width) - MIN_LEAF_SIZE + 1);

        if (splitHorizontally) {
            this.leftChild = new Leaf(this.x, this.y, this.width, splitPos);
            this.rightChild = new Leaf(this.x, this.y + splitPos, this.width, this.height - splitPos);
        } else {
            this.leftChild = new Leaf(this.x, this.y, splitPos, this.height);
            this.rightChild = new Leaf(this.x + splitPos, this.y, this.width - splitPos, this.height);
        }
        return true;
    }

    /**
     * Return the best split direction, in a way that minimizes disproportionate leaf. If the leaf
     * is close enough to a square, return a random boolean using Math.random() method
     * @return the best split direction
     */
    private boolean getBestSplitDirection() {
        double ratio = (double) this.width/this.height;
        if (this.width > this.height && ratio >= 1) {
            return false;
        } else if (this.height > this.width &&  ratio < 1) {
            return true;
        } else {
            return Math.random() > .5;
        }
    }

    /**
     * Check if we can split the leaf in a given direction. A leaf is "splittable" if it has no
     * children and splitting it won't result in a child smaller than MIN_LEAF_SIZE
     * @param horizontally True if we split horizontally, False if we split vertically
     * @return true if we can split the leaf, false otherwise
     */
    private boolean canSplit(boolean horizontally) {
        return (hasNoChildren() && (horizontally ? this.height : this.width) >= 2*MIN_LEAF_SIZE);
    }

    /**
     * Check if this leaf has already been split, i.e. if it has no children
     * @return True if the leaf has no children, false otherwise
     */
    private boolean hasNoChildren() {
        return (this.leftChild == null && this.rightChild == null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Leaf getLeftChild() {
        return leftChild;
    }

    public Leaf getRightChild() {
        return rightChild;
    }

    public String toString() {
        return String.format("Leaf of size (%d, %d) on (%d, %d)", height, width, x, y);
    }
}

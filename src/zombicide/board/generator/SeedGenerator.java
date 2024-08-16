package zombicide.board.generator;

import java.util.ArrayList;
import java.util.List;

public class SeedGenerator {
    private final int width;
    private final int height;
    private final Cell[][] terrain;
    private List<Leaf> leaves;


    /**
     * Create a new SeedGenerator object of given size.
     * @param width desired height of the map
     * @param height desired width of the map
     */
    public SeedGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.terrain = new Cell[height][width];
        this.leaves = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.terrain[i][j] = new Cell(0);
            }
        }
    }

    /**
     * Generate a new seed, consisting of a string of 0s and 1s, where 0 is a street and 1 is a room.
     * @return a string of 0s and 1s, where 0 is a street and 1 a room.
     */
    public String generateRandomSeed() {
        this.leaves = new ArrayList<>(); // reset leaves to allow successive generations
        Leaf root = new Leaf(0, 0, width, height);

        generateLeaves(root);
        writeLeavesOnTerrain();
        separateLeavesOnTerrain();

        return createSeedFromTerrain();
    }


    /**
     * Create a seed from terrain. You may want to generate it before like in generateRandomSeed :)
     * @return a string of 0s and 1s, where 0 is a street and 1 a room.
     * @see SeedGenerator#generateRandomSeed()
     */
    private String createSeedFromTerrain() {
        String seed = "";

        for (Cell[] line : this.terrain) {
            for (Cell cell : line) {
                if (cell.getId() < 0) { // cells of id<0 are streets
                    seed += "0";
                } else {
                    seed += "1";
                }
            }
        }

        return seed;
    }

    /**
     * BSP algorithm to split leaves recursively
     */
    private void generateLeaves(Leaf parent) {
        if (parent.split()) {
            generateLeaves(parent.getLeftChild());
            generateLeaves(parent.getRightChild());
        } else {
            this.leaves.add(parent);
        }
    }

    /**
     * Write the leaves on terrain, using a given list of leaves. This iterates through the leaves and
     * overrides each leaf on the previous one. Don't forget to generate leaves before !
     */
    private void writeLeavesOnTerrain() {
        int id = 1;
        for (Leaf leaf : this.leaves) {
            this.writeLeafOnTerrain(leaf, id++);
        }
    }

    /**
     * Write a given leaf with a given id on the terrain
     * @param leaf the leaf to write on terrain
     * @param id the id corresponding to the leaf
     */
    private void writeLeafOnTerrain(Leaf leaf, int id) {
        for (int i=0; i < leaf.getHeight(); i++) {
            for (int j=0; j < leaf.getWidth(); j++) {
                Cell cell = this.terrain[leaf.getY() + i][leaf.getX() + j];
                cell.setId(id);
            }
        }
    }

    /**
     * Separate leafs on terrain by placing cells of id -1, representing streets. This works by
     * comparing a cell's id to the id of the next cell.
     */
    private void separateLeavesOnTerrain() {
        for (int i=0; i < this.height-1; i++) {
            for (int j=0; j < this.width-1; j++) {
                int id = this.terrain[i][j].getId();
                if (id != this.terrain[i][j+1].getId() || id != this.terrain[i+1][j].getId()) {
                    this.terrain[i][j].setId(-1);
                }
            }
        }

        // place streets on the last row and the last column
        int lastRow = this.height - 1;
        for (int x=0; x < this.width; x++) {
            if (this.terrain[lastRow-1][x].getId() < 0) {
                this.terrain[lastRow][x].setId(-1);
            }
        }
        int lastCol = this.width - 1;
        for (int y=0; y < this.height; y++) {
            if (this.terrain[y][lastCol-1].getId() < 0) {
                this.terrain[y][lastCol].setId(-1);
            }
        }
    }
}

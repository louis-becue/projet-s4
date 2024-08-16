package zombicide.board.generator;

/**
 * Code for Cell class, used after BSP algorithm to represent leafs.
 */
public class Cell {
	
    private int id;

    /**
     * Create a new cell with given id, representing one leaf of the BSP algorithm
     * @param id a number corresponding to the leaf
     */
    public Cell(int id) {
        this.id = id;
    }

    /**
     * Return the id of the cell, corresponding to one leaf
     * @return the id of the cell
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the cell, corresponding to one leaf
     * @param id the new id of the cell
     */
    public void setId(int id) {
        this.id = id;
    }
}

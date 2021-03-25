package cpsc2150.extendedTicTacToe;
/**
 * A BoardPosition is a pair of integers that represents a set of coordinates on a board.
 *
 * @Invariant: N/A
 */
public class BoardPosition {

    private int row;
    private int column;

    /**
     *
     * @param   r selected row of index
     * @param   c selected column of index
     *
     * @post    row = r && column = c
     */
    BoardPosition(int r, int c){
        row = r;
        column = c;
    }
    /**
     * Returns the int value of this board position's row
     * @return row
     *
     * @post getRow = row
     */
    public int getRow(){
        return row;
    }

    /**
     * Returns the int value of this board position's column
     * @return column
     * @post getColumn = column
     */
    public int getColumn(){
        return column;
    }

    /**
     *
     * @param pos a board position to compare with this
     * @return true if the positions have equal row and column values
     *
     * @post    equals iff pos.getRow() == row && pos.getColumn == column
     */
    public boolean equals(BoardPosition pos){
        if(row == pos.getRow() && column == pos.getColumn()){
            return true;
        }

        return false;
    }

    /**
     *
     * @return a formatted string representing this
     *
     * @post toString = "<row>,<column>"
     */
    public String toString(){
        String out = getRow() + "," + getColumn();

        return out;
    }
}

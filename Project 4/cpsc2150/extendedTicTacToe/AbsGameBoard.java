package cpsc2150.extendedTicTacToe;

public abstract class AbsGameBoard implements IGameBoard {
    /**
     * Returns a formatted string representing the current layout of the board
     * @return a string representing this
     *
     * @post toString = [a formatted string representing the current state of the game board]
     */
    public String toString() {
        String out = "  |";

        //Column numbers
        for(int i=0; i <getNumColumns(); i++){
            if(i<10){
                out += " ";
            }
            out += i + "|";
        }
        out += "\n";

        //Row numbers and board data by row
        for(int i=0; i < getNumRows(); i++){
            if(i<10){
                out+=" ";
            }
            out += i + "|";
            for(int j=0; j < getNumColumns(); j++){
                BoardPosition nextPos = new BoardPosition(i,j);
                out += whatsAtPos(nextPos) + " |";
            }
            out+="\n";
        }
        return out;
    }
}

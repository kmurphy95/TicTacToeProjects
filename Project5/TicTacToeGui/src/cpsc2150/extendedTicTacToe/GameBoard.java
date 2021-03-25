package cpsc2150.extendedTicTacToe;

/**
 * Correspondence: GameBoard = board
 * Correspondence: numRow = number_of_rows
 * Correspondence: numCol = number_of_columns
 * Correspondence: numToWin = number_to_win
 *
 * @invariant numToWin < numRow && numToWin < numCol
 */
public class GameBoard extends AbsGameBoard {

    private char[][] board;
    private int numRow;
    private int numCol;
    private int numToWin;

    /**
     * Creates a new GameBoard that is empty, represented by a numRow x numCol char grid of spaces.
     *
     * @post    numRow = nR, numCol = nC, numToWin = nW, board = new char[numRow][numCol]
     *          board[0...numRow][0...numRow] = ' '
     */
    GameBoard(int nR, int nC, int nW){

        numRow = nR;
        numCol = nC;
        numToWin = nW;
        board = new char[numRow][numCol];

        for(int i = 0; i < numRow; i++){
            for(int j = 0; j < numCol; j++){
                board[i][j] = ' ';
            }
        }
    }

    public int getNumRows(){ return numRow; }

    public int getNumColumns(){ return numCol; }

    public int getNumToWin(){ return numToWin; }

    public void placeMarker(BoardPosition marker, char player)
    {
        board[marker.getRow()][marker.getColumn()] = player;
    }

    public char whatsAtPos(BoardPosition pos)
    {
        //If a position that is out of bounds is passed in, return an empty space
       if(  pos.getRow() >= 0 && pos.getRow() < getNumRows() &&
            pos.getColumn() >=0 && pos.getColumn() < getNumColumns()){
            return board[pos.getRow()][pos.getColumn()];
       }
       else{
           return ' ';
       }
    }
}

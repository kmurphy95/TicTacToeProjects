package cpsc2150.extendedTicTacToe;
/**
 * GameBoard represents a ticTacToe grid of varying size that depends on the number_of_rows and number_of_columns.
 * The game is meant to be played with two players, with player 1 always going first at the start of a game.
 * The goal of the game is for a player to place their markers one at a time into a line on the GameBoard while trying
 * to prevent their opponent from doing the same. Markers can be aligned in horizontal, vertical or diagonal
 * arrangements. Getting at least number_to_win of a player's markers in a line results in a victory.
 *
 * Initialization Ensures:  GameBoard is a grid of space characters of size number_of_rows
 *                          by number_of_columns.
 *
 *
 * Defines:     number_of_rows: Z
 *              number_of_columns: Z
 *              number_to_win: Z
 *
 * Constraints: number_to_win <= number_of_columns && number_to_win <= number_of_rows
 */
public interface IGameBoard {

    int MIN_ROWS = 3;
    int MIN_COLUMNS = 3;
    int MIN_TO_WIN = 3;
    int MAX_ROWS = 100;
    int MAX_COLUMNS = 100;
    int MIN_PLAYERS = 2;
    int MAX_PLAYERS = 10;

    /**
     *
     * @return the number of rows on the board
     *
     * @post getNumRows = number_of_rows
     */
    int getNumRows();

    /**
     *
     * @return the number of columns on the board
     *
     * @post getNumColumns = number_of_columns
     */
    int getNumColumns();

    /**
     *
     * @return the number of contiguous tiles needed to win the game
     *
     * @post getNumToWin = number_to_win
     */
    int getNumToWin();

    /**
     * Checks a given BoardPosition to see if it is in range and that no one has
     * placed their marker at those coordinates yet.
     *
     * @param pos a board position to evaluate for validity
     * @return true iff pos is valid and free
     *
     * @post checkSpace iff     0 <= pos.getRow() < number_of_rows &&
     *                          0 <= pos.getColumn() < number_of_columns &&
     *                          whatsAtPos(pos) == ' '
     */
    default boolean checkSpace(BoardPosition pos){
        {
            boolean checkFlag = true;

            //Is pos in bounds?
            if(pos.getColumn() < 0 || pos.getColumn() >= getNumColumns() ||
                    pos.getRow() < 0 || pos.getRow() >= getNumRows()){
                checkFlag = false;
            }
            else{
                //Is pos occupied?
                if(whatsAtPos(pos) != ' '){
                    checkFlag = false;
                }
            }
            return checkFlag;
        }
    }

    /**
     * Changes the character at the validated location on the board specified by marker.
     *
     * @param marker location on board to place marker
     * @param player chosen character icon of current player
     *
     * @pre   checkSpace(marker)
     * @post  GameBoard[marker.getRow()][marker.getColumn()] = player
     */
    void placeMarker(BoardPosition marker, char player);

    /**
     * Checks to see if the marker placed at lastPos resulted in a victory condition.
     *
     * @param lastPos location on the board where the placeMarker was last called
     * @return true if any of the win conditions were met
     *
     * @pre     whatsAtPos(lastPos) != ' '
     * @post    checkForWinner iff      checkHorizontalWin(lastPos, whatsAtPos(lastPos)) &&
     *                                  checkVerticalWin(lastPos, whatsAtPos(lastPos)) &&
     *                                  checkDiagonalWin(lastPos, whatsAtPos(lastPos)) &&
     */
    default boolean checkForWinner(BoardPosition lastPos){

        return(checkHorizontalWin(lastPos,whatsAtPos(lastPos)) ||
                checkVerticalWin(lastPos,whatsAtPos(lastPos)) ||
                checkDiagonalWin(lastPos,whatsAtPos(lastPos)));
    }

    /**
     * Checks to see if the board is full.
     * The board is full when all spaces are occupied by player markers.
     *
     * @return  true iff the board is full
     *
     * @post    GameBoard[0...number_of_rows-1][0...number_of_columns-1] != ' '
     */
    default boolean checkForDraw(){
        BoardPosition nextPos;

        //If any spaces on the board are still available to make a legal move
        //return a draw. This only happens when the board is full.
        for(int i=0; i < getNumRows(); i++){
            for(int j=0; j < getNumColumns(); j++){
                nextPos = new BoardPosition(i,j);
                if(checkSpace(nextPos)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks to see if the last placed marker resulted in a victory condition in a horizontal line.
     *
     * @param lastPos location on the board where the placeMarker was last called
     * @param player player marker at lastPos
     * @return true if there are at least number_to_win horizontal board spaces contiguous with lastPos
     *
     *
     * @post checkHorizontalWin iff     [at least number_to_win contiguous BoardPositions are marked with player
     *                                   in a horizontal line that includes LastPos in the GameBoard]
     */
    default boolean checkHorizontalWin(BoardPosition lastPos, char player)
    {
        int count = 0;
        int row = lastPos.getRow();
        int col = lastPos.getColumn();

        BoardPosition nextPosition = new BoardPosition(row,col);

        //Check right of lastPos
        while(col < getNumColumns() && isPlayerAtPos(nextPosition,player)){
            col++;
            count++;
            nextPosition = new BoardPosition(row,col);
        }

        col = lastPos.getColumn() - 1;
        nextPosition = new BoardPosition(row,col);

        //Check left of lastPos
        while(col >= 0 && isPlayerAtPos(nextPosition,player)){
            col--;
            count++;
            nextPosition = new BoardPosition(row,col);
        }

        return count >= getNumToWin();
    }

    /**
     *
     * @param lastPos location on the board where the placeMarker was last called
     * @param player player marker at lastPos
     * @return true if there are at least number_to_win vertical board spaces contiguous with lastPos
     *
     * @post checkVerticalWin iff     [at least number_to_win contiguous BoardPositions are marked with player
     *                                 in a vertical line that includes LastPos in the GameBoard]
     */
    default boolean checkVerticalWin(BoardPosition lastPos, char player)
    {
        int count = 0;
        int row = lastPos.getRow();
        int col = lastPos.getColumn();

        BoardPosition nextPosition = new BoardPosition(row,col);

        //Check under lastPos
        while(row < getNumRows() && isPlayerAtPos(nextPosition,player)){
            row++;
            count++;
            nextPosition = new BoardPosition(row,col);
        }

        row = lastPos.getRow() - 1;
        nextPosition = new BoardPosition(row,col);

        //Check above lastPos
        while(row >= 0 && isPlayerAtPos(nextPosition,player)){
            row--;
            count++;
            nextPosition = new BoardPosition(row,col);
        }

        return count >= getNumToWin();
    }

    /**
     *
     * @param lastPos location on the board where the placeMarker was last called
     * @param player player marker at lastPos
     * @return true if there are at least number_to_win diagonal board spaces contiguous with lastPos
     *
     * @post checkDiagonalWin iff     [at least number_to_win contiguous BoardPositions are marked with player
     *                                in a major or minor diagonal line that includes LastPos in the GameBoard]
     */
    default boolean checkDiagonalWin(BoardPosition lastPos, char player)
    {
        int count = 0;
        int row = lastPos.getRow();
        int col = lastPos.getColumn();
        BoardPosition nextPos = new BoardPosition(row,col);

        //Check main (down and right)
        while(row < getNumRows() && col < getNumColumns() && isPlayerAtPos(nextPos,player)){
            col++;
            row++;
            count++;
            nextPos = new BoardPosition(row,col);
        }
        row = lastPos.getRow() - 1;
        col = lastPos.getColumn() - 1;
        nextPos = new BoardPosition(row,col);

        //Check main (up and left)
        while(row >= 0 && col >= 0 && isPlayerAtPos(nextPos,player)){
            col--;
            row--;
            count++;
            nextPos = new BoardPosition(row,col);
        }

        if(count >= getNumToWin()){
            return true;
        }

        //Next diagonal starts here (minor)
        count = 0;
        row = lastPos.getRow();
        col = lastPos.getColumn();
        nextPos = new BoardPosition(row,col);

        //Check minor (down and left)
        while(row < getNumRows() && col >= 0 && isPlayerAtPos(nextPos,player)){
            col--;
            row++;
            count++;
            nextPos = new BoardPosition(row,col);
        }

        row = lastPos.getRow() - 1;
        col = lastPos.getColumn() + 1;
        nextPos = new BoardPosition(row,col);

        //Check minor (up and right)
        while(row >= 0 && col < getNumColumns() && isPlayerAtPos(nextPos,player)){
            row--;
            col++;
            count++;
            nextPos = new BoardPosition(row,col);
        }

        return count >= getNumToWin();
    }

    /**
     * Returns whether or a given position is occupied by a specified player's marker
     *
     * @param pos Position to check
     * @param player character to look for
     * @return true if board at pos is taken by player's marker
     *
     * @pre  0 <= pos.getRow() < number_of_rows && 0 <= pos.getColumn() < number_of_columns
     * @post isPlayerAtPos if whatsAtPos(pos) == player
     *
     */
    default boolean isPlayerAtPos(BoardPosition pos, char player){
        {
            return whatsAtPos(pos) == player;
        }
    }

    /**
     * Returns the character that is current at a given board position.
     *
     * @param pos a board position to check the contents of
     * @return the character in board at pos
     *
     * @post    if(pos.getRow () >= 0 && pos.getRow() < numRow &&
     *             pos.getColumn() >=0 && pos.getColumn() < numCol)
     *             whatsAtPos = GameBoard[pos.getRow()][pos.getColumn]
     *             else
     *             whatsAtPos = ' '
     *
     */
    char whatsAtPos(BoardPosition pos);
}

package cpsc2150.extendedTicTacToe;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestGameBoardMem{

    private GameBoardMem makeGameBoard(int row, int col, int win) {
        return new GameBoardMem(row, col, win);
    }

    private String stringMatch(char[][] inArray, int rows, int cols){
        String out = "  |";

        //Column numbers
        for(int i=0; i < cols; i++){
            if(i<10){
                out += " ";
            }
            out += i + "|";
        }
        out += "\n";

        //Row numbers and board data by row
        for(int i=0; i < rows; i++){
            if(i<10){
                out+=" ";
            }
            out += i + "|";
            for(int j=0; j < cols; j++){
                out += inArray[i][j] + " |";
            }
            out+="\n";
        }
        return out;
    }


    //Constructor

    @Test
    public void test_construct_MINROW_MINCOL_MINWIN() { //tests smallest size

        char[][] outCompare = new char[IGameBoard.MIN_ROWS][IGameBoard.MIN_COLUMNS];

        for(int i = 0; i < IGameBoard.MIN_ROWS;i++){
            for(int j = 0; j < IGameBoard.MIN_COLUMNS;j++){
                outCompare[i][j] = ' ';
            }
        }

        IGameBoard gb = makeGameBoard(IGameBoard.MIN_ROWS, IGameBoard.MIN_COLUMNS, IGameBoard.MIN_TO_WIN);

        assertTrue(gb.toString().equals(stringMatch(outCompare,IGameBoard.MIN_ROWS,IGameBoard.MIN_COLUMNS)) &&
                gb.getNumToWin() == IGameBoard.MIN_TO_WIN);

    }

    @Test
    public void test_construct_MAXROW_MAXCOL_win_30() {                //tests large size
        char[][] outCompare = new char[IGameBoard.MAX_ROWS][IGameBoard.MAX_COLUMNS];

        for(int i = 0; i < IGameBoard.MAX_ROWS;i++){
            for(int j = 0; j < IGameBoard.MAX_COLUMNS;j++){
                outCompare[i][j] = ' ';
            }
        }

        IGameBoard gb = makeGameBoard(IGameBoard.MAX_ROWS, IGameBoard.MAX_COLUMNS, 30);

        assertTrue(gb.toString().equals(stringMatch(outCompare,IGameBoard.MAX_ROWS,IGameBoard.MAX_COLUMNS)) &&
                gb.getNumToWin() == 30);
    }

    @Test
    public void test_construct_row_10_col_8_win_4() {              //tests general case
        char[][] outCompare = new char[10][8];

        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.getNumToWin() == 4);
    }

    @Test
    public void test_construct_MAXROW_MINCOL_MINWIN() {            //tests wildly disproportionate board
        char[][] outCompare = new char[IGameBoard.MAX_ROWS][IGameBoard.MIN_COLUMNS];
        for(int i = 0; i < IGameBoard.MAX_ROWS;i++){
            for(int j = 0; j < IGameBoard.MIN_COLUMNS;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(IGameBoard.MAX_ROWS, IGameBoard.MIN_COLUMNS, IGameBoard.MIN_TO_WIN);

        assertTrue(gb.toString().equals(stringMatch(outCompare,IGameBoard.MAX_ROWS,IGameBoard.MIN_COLUMNS)) &&
                gb.getNumToWin() == IGameBoard.MIN_TO_WIN);
    }



    //Check Space

    @Test
    public void test_chSpace_out_of_bounds() {                         //Checks space with invalid coords
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);
        nextPos = new BoardPosition(1,1);
        gb.placeMarker(nextPos,'X');
        outCompare[1][1] = 'X';
        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'O');
        outCompare[2][2] = 'O';
        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'X');
        outCompare[4][4] = 'X';

        nextPos = new BoardPosition(2,2);     //Check this position

        assertFalse(gb.toString().equals(stringMatch(outCompare, 10, 8)) && gb.checkSpace(nextPos));

    }

    @Test
    public void test_chSpace_space_occupied() {                       //Checks space that has been played on
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(1,1);
        gb.placeMarker(nextPos,'X');
        outCompare[1][1] = 'X';
        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'O');
        outCompare[2][2] = 'O';
        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'X');
        outCompare[4][4] = 'X';

        nextPos = new BoardPosition(6,2);     //Check this position

        assertTrue(gb.toString().equals(stringMatch(outCompare, 10, 8)) && gb.checkSpace(nextPos));
    }

    @Test
    public void test_chSpace_valid_space() {                         //Checks space that is in range and empty
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }

        IGameBoard gb = makeGameBoard(10, 8, 4);
        nextPos = new BoardPosition(1,1);
        gb.placeMarker(nextPos,'X');
        outCompare[1][1] = 'X';
        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'O');
        outCompare[2][2] = 'O';
        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'X');
        outCompare[4][4] = 'X';

        nextPos = new BoardPosition(-5,15);     //Check this position

        assertFalse(gb.toString().equals(stringMatch(outCompare, 10, 8)) && gb.checkSpace(nextPos));
    }



    //Check Horizontal

    @Test
    public void test_hWin_horiz_left_bound() {                           //lastPos.col is less than number_to_win from the left side
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        //LASTPOS
        nextPos = new BoardPosition(1,1); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkHorizontalWin(nextPos,'X'));

    }

    @Test
    public void test_hWin_horiz_right_bound() {                          //lastPos.col is less than number_to_win fewer than MAXCOL
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        //LASTPOS
        nextPos = new BoardPosition(3,5); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkHorizontalWin(nextPos,'O'));
    }

    @Test
    public void test_hWin_horiz_loss() {                    //Checks case with number_to_win markers aligned
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        //LASTPOS
        nextPos = new BoardPosition(6,5); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                !gb.checkHorizontalWin(nextPos,'O'));
    }

    @Test
    public void test_hWin_horiz_win_count_excess() {                    //Checks case where more than number_to_win markers are aligned
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        nextPos = new BoardPosition(1,4); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');
        nextPos = new BoardPosition(1,1); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkHorizontalWin(nextPos,'X'));
    }



    //Check vertical

    @Test
    public void test_vWin_vert_upper_bound() {                          //lastPos.row is less than number_to_win from row 0
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        //LASTPOS
        nextPos = new BoardPosition(2,7); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkVerticalWin(nextPos,'O'));
    }

    @Test
    public void test_vWin_vert_lower_bound() {                          //lastPos.row is less than number_to_win fewer than MAXROW
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        //LASTPOS
        nextPos = new BoardPosition(9,6); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkVerticalWin(nextPos,'O'));
    }

    @Test
    public void test_vWin_vert_loss() {                    //Checks case with number_to_win markers aligned
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        //LASTPOS
        nextPos = new BoardPosition(4,6); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                !gb.checkVerticalWin(nextPos,'O'));
    }

    @Test
    public void test_vWin_vert_win_count_excess() {                    //Checks case where more than number_to_win markers are aligned
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        nextPos = new BoardPosition(4,6); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');
        nextPos = new BoardPosition(5,6); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkVerticalWin(nextPos,'O'));
    }



    //Check diagonal

    @Test
    public void test_dWin_top_right_bound() {                      //row is less than number_to_win from row 0 and col is less than number_to_win from MAXCOL
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        //LASTPOS
        nextPos = new BoardPosition(1,6); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkDiagonalWin(nextPos,'O'));
    }

    @Test
    public void test_dWin_bottom_right_bound() {                   //row is less than number to win from MAXROW and col is less than number_to_WIN from col 0
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        //LASTPOS
        nextPos = new BoardPosition(9,7); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkDiagonalWin(nextPos,'O'));
    }

    @Test
    public void test_dWin_top_left_bound() {                       //row is less than number to win from row 0 and col is less than number to win from col 0
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        //LASTPOS
        nextPos = new BoardPosition(1,1); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkDiagonalWin(nextPos,'X'));
    }

    @Test
    public void test_dWin_majorD_loss_corner() {              //hits two bounds, impossible to win
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        //LASTPOS
        nextPos = new BoardPosition(8,1); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                !gb.checkDiagonalWin(nextPos,'O'));
    }

    @Test
    public void test_dWin_minorDwin_count_excess() {               //more than number_to_win markers are aligned
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        //LASTPOS
        nextPos = new BoardPosition(4,4); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');
        nextPos = new BoardPosition(1,1); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkDiagonalWin(nextPos,'X'));
    }

    @Test
    public void test_dWin_bottom_left_bound() {                  //row is less than number to win from MAXROW and col is less than number_to_win from MAXCOL
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        //LASTPOS
        nextPos = new BoardPosition(8,1); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                gb.checkDiagonalWin(nextPos,'X'));
    }

    @Test
    public void test_dWin_majorD_loss() {
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(2,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(1,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(9,0); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(7,2); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(6,3); //X
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');

        nextPos = new BoardPosition(3,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(2,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(3,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(6,4); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,5); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(8,6); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(7,0); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');

        nextPos = new BoardPosition(0,7); //O
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';
        gb.placeMarker(nextPos,'O');


        //LASTPOS
        nextPos = new BoardPosition(4,4); //LAST POS
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';
        gb.placeMarker(nextPos,'X');



        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) &&
                !gb.checkDiagonalWin(nextPos,'X'));
    }



    //Check draw

    @Test
    public void test_board_full_found_draw() {                //full board
        char[][] outCompare = new char[10][8];
        char[] letters = {'A','B','C','D','E','F','G','H','I','J'};
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }

        IGameBoard gb = makeGameBoard(10, 8, 4);

        int i=0;
        for(int j = 0; j < gb.getNumRows(); j++){
            for(int k=0; k<gb.getNumColumns();k++){

                nextPos = new BoardPosition(j,k);
                gb.placeMarker(nextPos,letters[i]);
                outCompare[j][k] = letters[i++];

                if(i >= 10){
                    i=0;
                }
            }
        }

        assertTrue(gb.checkForDraw() && gb.toString().equals(stringMatch(outCompare,10,8)));

    }

    @Test
    public void test_board_empty_no_draw() {
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }


        IGameBoard gb = makeGameBoard(10, 8, 4);

        assertFalse(gb.checkForDraw());
    }

    @Test
    public void test_no_winner_no_draw() {                    //middle of an unresolved game
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(0,0);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(5,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(1,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(3,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) && !gb.checkForDraw());
    }

    @Test
    public void test_found_winner_found_draw() {             //Winner present, board is full
        char[][] outCompare = new char[10][8];
        char[] letters = {'A','B','C','D','E','F','G','H','I'};
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);


        int i=0;
        for(int j = 0; j < gb.getNumRows(); j++) {
            for (int k = 0; k < gb.getNumColumns(); k++) {

                if (j == k) {
                    nextPos = new BoardPosition(j, k);
                    gb.placeMarker(nextPos, 'X');
                    outCompare[j][k] = 'X';
                    k++;
                }

                if (k < gb.getNumColumns()) {
                    nextPos = new BoardPosition(j, k);
                    gb.placeMarker(nextPos, letters[i]);
                    outCompare[j][k] = letters[i++];

                    if (i >= 9) {
                        i = 0;
                    }
                }
            }
        }
        assertTrue(gb.toString().equals(stringMatch(outCompare,10,8)) && gb.checkForDraw());
    }



    //Check whatsAtPos

    @Test
    public void test_whatsAt_pos_empty() {                           //Space valid but empty
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(0,0);

        assertEquals(gb.whatsAtPos(nextPos),' ');

    }

    @Test
    public void test_whatsAt_row_valid_column_over_max() {            //Column > MAX COL
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,15);

        assertEquals(gb.whatsAtPos(nextPos),' ');
    }

    @Test
    public void test_whatsAt_column_valid_row_over_max() {            //Row > MAX ROW
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(15,5);

        assertEquals(gb.whatsAtPos(nextPos),' ');


    }

    @Test
    public void test_whatsAt_pos_taken_in_range() {                   //Space valid and contains a character
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(2,2);

        assertEquals(gb.whatsAtPos(nextPos),'X');
    }

    @Test
    public void test_whatsAt_pos_negative() {                     //Row and Column < 0
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(-3,-3);

        assertEquals(gb.whatsAtPos(nextPos),' ');
    }



    //Check isPlayerAtPos

    @Test
    public void test_playerAt_pos_empty_no_match(){                     //Position empty, no match
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(0,0);

        assertFalse(gb.isPlayerAtPos(nextPos,'X'));
    }

    @Test
    public void test_playerAt_valid_no_match(){                         //Position taken, no match
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(2,6);

        assertFalse(gb.isPlayerAtPos(nextPos,'X'));
    }

    @Test
    public void test_playerAt_valid_match(){                            //Position taken, match found
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(2,2);

        assertTrue(gb.isPlayerAtPos(nextPos,'X'));
    }

    @Test
    public void test_playerAt_pos_empty_match(){                        //player passed in is ' ' and space is empty
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(0,0);

        assertTrue(gb.isPlayerAtPos(nextPos,' '));
    }

    @Test
    public void test_playerAt_valid_blank_player(){                     //player is equal to blank space, but pos is taken
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(2,2);

        assertFalse(gb.isPlayerAtPos(nextPos,' '));
    }



    //Test place marker

    @Test
    public void test_place_top_right(){                        //Checks upper right bound (max col, min row)
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(0,7);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        assertEquals(gb.toString(),stringMatch(outCompare,10,8));
    }

    @Test
    public void test_place_bottom_right(){                     //lower right bound (max col, max row)
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(9,7);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        assertEquals(gb.toString(),stringMatch(outCompare,10,8));
    }

    @Test
    public void test_place_top_left(){                         //upper left/origin bound (min row, min col)
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(0,0);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        assertEquals(gb.toString(),stringMatch(outCompare,10,8));
    }

    @Test
    public void test_place_bottom_left(){                      //bottom left (max row, min col)
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10, 8, 4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(9,7);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        assertEquals(gb.toString(),stringMatch(outCompare,10,8));
    }

    @Test
    public void test_place_new_player() {                         //General case, a new player is added that was not present before
        char[][] outCompare = new char[10][8];
        BoardPosition nextPos;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++){
                outCompare[i][j] = ' ';
            }
        }
        IGameBoard gb = makeGameBoard(10,8,4);

        nextPos = new BoardPosition(2,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(2,6);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(4,3);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(6,2);
        gb.placeMarker(nextPos,'X');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'X';

        nextPos = new BoardPosition(4,4);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(5,5);
        gb.placeMarker(nextPos,'O');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'O';

        nextPos = new BoardPosition(2,4);
        gb.placeMarker(nextPos,'Z');
        outCompare[nextPos.getRow()][nextPos.getColumn()] = 'Z';

        assertEquals(gb.toString(),stringMatch(outCompare,10,8));
    }
}

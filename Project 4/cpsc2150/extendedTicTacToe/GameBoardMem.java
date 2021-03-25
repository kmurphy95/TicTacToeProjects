package cpsc2150.extendedTicTacToe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoardMem extends AbsGameBoard{

    private int numRows;
    private int numCols;
    private int numToWin;
    private Map<Character, List<BoardPosition>> board;

    GameBoardMem(int nR, int nC, int nW){
        numRows=nR;
        numCols=nC;
        numToWin=nW;

        board = new HashMap<>();
    }

    public int getNumColumns(){
        return numCols;
    }

    public int getNumRows(){
        return numRows;
    }

    public int getNumToWin(){
        return numToWin;
    }

    public void placeMarker(BoardPosition pos, char player){
        List<BoardPosition> newPlayer = new ArrayList<>();
            if(board.containsKey(player)){
                board.get(player).add(pos);
            }
            else{
                newPlayer.add(pos);
                board.put(player,newPlayer);
            }
    }

    public char whatsAtPos(BoardPosition pos){
        if( pos.getRow() >= 0 && pos.getRow() < getNumRows() &&
                pos.getColumn() >=0 && pos.getColumn() < getNumColumns()){
            for(Map.Entry<Character,List<BoardPosition>> m: board.entrySet()){
                for(BoardPosition nextPos: m.getValue()){
                    if(nextPos.equals(pos)){
                        return m.getKey();
                    }
                }
            }
        }
        return ' ';
    }

    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player){

        if(board.containsKey(player)){
            for(BoardPosition nextPos: board.get(player)){
                if(nextPos.equals(pos)){
                    return true;
                }
            }
        }
        else{
            if(player == ' '){
                return checkSpace(pos);
            }

        }
        return false;
    }
    
}

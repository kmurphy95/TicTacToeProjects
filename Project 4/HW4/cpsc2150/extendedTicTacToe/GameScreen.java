package cpsc2150.extendedTicTacToe;

import java.util.Scanner;

/**
 *
 * @Invariant: N/A
 */
public class GameScreen {

    /**
     * Creates a new valid board position at the chosen coordinates [row,column].
     *
     * @param theBoard the current game board data
     * @param scanner  an input scanner
     * @param player   the current player's marker
     * @return a BoardPosition at user's chosen position
     * @pre [scanner can input integers]
     * @post makePosition = new BoardPosition([user row input],[user column input]) &&
     * theBoard.checkSpace(makePosition)
     */
    public static BoardPosition makePosition(IGameBoard theBoard, Scanner scanner, char player) {

        int userInputR, userInputC;
        BoardPosition newPosition = new BoardPosition(-1, -1);

        while (!theBoard.checkSpace(newPosition)) {

            System.out.println("Player " + player + " Please enter your ROW");
            userInputR = scanner.nextInt();
            System.out.println("Player " + player + " Please enter your COLUMN");
            userInputC = scanner.nextInt();

            newPosition = new BoardPosition(userInputR, userInputC);
            if (!theBoard.checkSpace(newPosition)) {
                System.out.println(theBoard);
                System.out.println("That space is unavailable, please pick again");
            }
        }

        return newPosition;
    }

    public static IGameBoard makeBoard(Scanner scanner) {
        int userRowsInput, userColumnsInput, userWinNumber;
        String implementation;
        IGameBoard outBoard;

        //Ask user for rows
        System.out.println("How many rows?");
        userRowsInput = scanner.nextInt();
        while (userRowsInput < IGameBoard.MIN_ROWS || userRowsInput > IGameBoard.MAX_ROWS) {
            System.out.println("Rows must be between " + IGameBoard.MIN_ROWS + " and " + IGameBoard.MAX_ROWS);
            System.out.println("How many rows?");
            userRowsInput = scanner.nextInt();
        }
        //Ask user for columns
        System.out.println("How many columns?");
        userColumnsInput = scanner.nextInt();
        while (userColumnsInput < IGameBoard.MIN_COLUMNS || userColumnsInput > IGameBoard.MAX_COLUMNS) {
            System.out.println("Columns must be between " + IGameBoard.MIN_COLUMNS + " and " + IGameBoard.MAX_COLUMNS);
            System.out.println("How many columns?");
            userColumnsInput = scanner.nextInt();
        }
        //Ask for number to win
        System.out.println("How many in a row to win?");
        userWinNumber = scanner.nextInt();
        while (userWinNumber < IGameBoard.MIN_TO_WIN || userWinNumber > userColumnsInput ||
                userWinNumber > userRowsInput) {
            if (userWinNumber < IGameBoard.MIN_TO_WIN) {
                System.out.println("Number to win must be at least " + IGameBoard.MIN_TO_WIN);
            } else {
                if (userColumnsInput < userRowsInput) {
                    System.out.println("Number to win must be less than " + userColumnsInput);
                } else {
                    System.out.println("Number to win must be less than " + userRowsInput);
                }
            }
            userWinNumber = scanner.nextInt();
        }

        scanner.nextLine(); //empty buffer
        System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game(M/m)?");
        implementation = scanner.nextLine();
        while ( !implementation.equals("f") && !implementation.equals("F") &&
                !implementation.equals("m") && !implementation.equals("M")) {
            System.out.println("Please enter F or M");
            implementation = scanner.nextLine();
        }

         if(implementation.charAt(0) == 'f' || implementation.charAt(0) == 'F'){
        outBoard = new GameBoard(userRowsInput, userColumnsInput, userWinNumber);
         }
           else{
           outBoard = new GameBoardMem(userRowsInput,userColumnsInput,userWinNumber);
           }
        return outBoard;
    }

    private static int getPlayerCount(Scanner scanner) {
        int userPlayersInput;
        System.out.println("How many players?");
        userPlayersInput = scanner.nextInt();
        while (userPlayersInput < IGameBoard.MIN_PLAYERS || userPlayersInput > IGameBoard.MAX_PLAYERS) {
            if (userPlayersInput < IGameBoard.MIN_PLAYERS) {
                System.out.println("Must be at least " + IGameBoard.MIN_PLAYERS + " players");
            } else {
                System.out.println("Must be " + IGameBoard.MAX_PLAYERS + " players or fewer");
            }
            System.out.println("How many players?");
            userPlayersInput = scanner.nextInt();
        }

        return userPlayersInput;
    }

    private static char getNextMarker(Scanner scanner, char[] roster, int player) {
        String nextMarker = null;
        boolean markerTaken = true;


        while (markerTaken) {
            markerTaken = false;
            System.out.println("Enter the character to represent player " + (player + 1));
            nextMarker = scanner.next();
            //For every character on the roster, ensure it is not the same as the user's input
            for (char c : roster) {
                if (c == nextMarker.toUpperCase().charAt(0)) {
                    markerTaken = true;
                }
            }
            if (markerTaken) {
                System.out.println(nextMarker.toUpperCase().charAt(0) + " is already taken as a player token!");
            }
        }
        return nextMarker.toUpperCase().charAt(0);
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        IGameBoard theBoard;
        BoardPosition nextPosition;
        char[] roster;
        String playAgain = "Y";
        int userPlayersInput;
        boolean winnerFound;


        //Start user specifications
        while (playAgain.equals("Y") || playAgain.equals("y")) {

            userPlayersInput = getPlayerCount(scanner);
            roster = new char[userPlayersInput];

            //Populate roster with unique chars
            for (int k = 0; k < userPlayersInput; k++) {
                roster[k] = getNextMarker(scanner, roster, k);
            }
            theBoard = makeBoard(scanner);

            winnerFound = false;
            //Start game
            while (!winnerFound) {
                int i = 0;
                while (i < userPlayersInput && !winnerFound) {
                    System.out.println(theBoard);
                    nextPosition = makePosition(theBoard, scanner, roster[i]);
                    theBoard.placeMarker(nextPosition, roster[i]);
                    winnerFound = theBoard.checkForWinner(nextPosition);
                    i++;
                }

                if (winnerFound) {
                    System.out.println("Player " + roster[i - 1] + " wins!");
                }
                else {
                    if(theBoard.checkForDraw()) {
                        System.out.println("It's a draw!");
                        winnerFound = true;
                    }
                }

            //Ask player to play again, if a winner has been found or a draw has occurred
            if (winnerFound) {
                scanner.nextLine();
                System.out.println("Would you like to play again? Y/N");
                playAgain = scanner.nextLine();

                while (!playAgain.equals("Y") && !playAgain.equals("y") &&
                        !playAgain.equals("N") && !playAgain.equals("n")) {
                    System.out.println("Invalid selection. Please enter Y or N");
                    playAgain = scanner.nextLine();
                }
            }
         } //Current game
        } //Play again loop
    }//main

}//class

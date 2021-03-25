package cpsc2150.extendedTicTacToe;

/**
 * The TicTacToe controller class will handle communication between our TicTacToeView and our Model (IGameBoard and BoardPosition)
 *
 * This is where you will write code
 *
 * You will need to include your BoardPosition class, the IGameBoard interface
 * and the implementations from previous homeworks
 * If your code was correct you will not need to make any changes to your IGameBoard classes
 */
public class TicTacToeController{
    //our current game that is being played
    private IGameBoard curGame;

    //The screen that provides our view
    private TicTacToeView screen;


    public static final int MAX_PLAYERS = 10;

    private int number_of_players;          //Number of players involved in the current game
    private static final char [] playerMarker = {'X','O','Z','V','P','H','I','G','M','A'}; //potential players
    private int playerNumber;           //Represents the index of playerMarker that is the current player
    private boolean winnerFound;      //Keeps track of whether or not the board is in a winning state

    /**
     *
     * @param model the board implementation
     * @param view the screen that is shown
     * @param np The number of players for the game
     * @post the controller will respond to actions on the view using the model.
     */
    TicTacToeController(IGameBoard model, TicTacToeView view, int np){
        this.curGame = model;
        this.screen = view;
        this.number_of_players = np;

        playerNumber=0;         //Start with player 0 ('X')
        winnerFound=false;
    }

    /**
     *
     * @param row the row of the activated button
     * @param col the column of the activated button
     * @pre row and col are in the bounds of the game represented by the view
     * @post The button pressed will show the right token and check if a player has won.
     */
    public void processButtonClick(int row, int col) {

        //If the board is in a victory or tie state, reset the board and go back to the setup screen
        if(curGame.checkForDraw() || winnerFound){
            winnerFound=false;
            newGame();
        }
        else{
            BoardPosition posClicked = new BoardPosition(row,col);

            //If the position that was clicked was valid, go ahead and handle placing it
            if(curGame.checkSpace(posClicked)){

                curGame.placeMarker(posClicked,playerMarker[playerNumber]);
                screen.setMarker(row,col,playerMarker[playerNumber]);

                winnerFound=curGame.checkForWinner(posClicked);

                //Print winner message, or move on to next player
                if(winnerFound){
                    screen.setMessage("Player " + playerMarker[playerNumber] + " wins! Click any space to play again.");
                }
                else{
                    playerNumber++;

                    //Reset the player back to player 0 (x) once all other players have made their move
                    if(playerNumber >= number_of_players){
                        playerNumber=0;
                    }

                    //If no one has won yet, announce if the game has ended in a draw,
                    // otherwise notify the next player that it is their turn
                    if(curGame.checkForDraw()) {
                        screen.setMessage("It's a draw... Click any space to play again.");
                    }
                    else{
                        screen.setMessage("Player " + playerMarker[playerNumber] + "'s turn");
                    }
                }

            }
            else{ //Player chose an invalid/full space
                screen.setMessage("Position full. Choose again Player " + playerMarker[playerNumber]);
            }

        }
    }

    private void newGame()
    {
        screen.dispose();
        GameSetupScreen screen = new GameSetupScreen();
        GameSetupController controller = new GameSetupController(screen);
        screen.registerObserver(controller);
    }
}

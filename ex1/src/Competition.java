import java.util.Scanner;

/**
 * The Competition class represents a Nim competition between two players, consisting of a given number of rounds. 
 * It also keeps track of the number of victories of each player.
 */
public class Competition {
	private final Player player1;
    private final Player player2;
    private final boolean displayMessage;
    private int p1Wins = 0;
    private int p2Wins = 0;

	public Competition(Player player1, Player player2, boolean displayMessage){
        this.player1 = player1;
        this.player2 = player2;
        this.displayMessage = displayMessage;
	}

    /**
     * Rturns the score for the give player ID
     * @param playerPosition player Id number
     * @return the winning score of the player.
     */
    public int getPlayerScore(int playerPosition){
        switch (playerPosition){
            case 1:
                return p1Wins;
            case 2:
                return p2Wins;

            default:
                return -1;
        }
    }

    /**
     * Play a given number of games.
     * @param numRounds number of games to play.
     */
    public void playMultiple(int numRounds){
        for (int i = 0; i < numRounds; i++) {
            Player currentPlayer = player1; // Set the current player.

            Board board = new Board();
            printMessage("Welcome to the sticks game!");

            // Play the game until there are no unmarked sticks on the board.
            while(board.getNumberOfUnmarkedSticks() > 0){
                Move currentMove;
                printMessage("Player " + currentPlayer.getPlayerId() + ", it is now your turn!");

                /* Get a valid move from the player and try playing.
                    in case the move is invalid, ask the player to try another move.
                 */
                while(true) {
                    currentMove = currentPlayer.produceMove(board);
                    if(board.markStickSequence(currentMove) < 0)
                        printMessage("Invalid move. Enter another:");
                    else {
                        break;
                    }
                }

                printMessage("Player " + currentPlayer.getPlayerId() + " made the move: " + currentMove);

                // Swith players.
                if(currentPlayer.equals(player1))
                    currentPlayer = player2;
                else
                    currentPlayer = player1;
            }

            // Increase the score of the winning player.
            switch (currentPlayer.getPlayerId()){
                case 1:
                    p1Wins++;
                    break;
                case 2:
                    p2Wins++;
                    break;
            }

            printMessage("Player " + currentPlayer.getPlayerId() + " won!");



        }
    }
	
	/*
	 * Returns the integer representing the type of player 1; returns -1 on bad
	 * input.
	 */
	private static int parsePlayer1Type(String[] args){
		try{
			return Integer.parseInt(args[0]);
		} catch (Exception E){
			return -1;
		}
	}
	
	/*
	 * Returns the integer representing the type of player 2; returns -1 on bad
	 * input.
	 */
	private static int parsePlayer2Type(String[] args){
		try{
			return Integer.parseInt(args[1]);
		} catch (Exception E){
			return -1;
		}
	}
	
	/*
	 * Returns the integer representing the type of player 2; returns -1 on bad
	 * input.
	 */
	private static int parseNumberOfGames(String[] args){
		try{
			return Integer.parseInt(args[2]);
		} catch (Exception E){
			return -1;
		}
	}

    /**
     * Prints a given string if displayMessage is set to true.
     * @param str String to print to the console.
     */
    private void printMessage(String str){
        if(displayMessage)
            System.out.println(str);
    }

	/**
	 * The method runs a Nim competition between two players according to the three user-specified arguments. 
	 * (1) The type of the first player, which is a positive integer between 1 and 4: 1 for a Random computer
	 *     player, 2 for a Heuristic computer player, 3 for a Smart computer player and 4 for a human player.
	 * (2) The type of the second player, which is a positive integer between 1 and 4.
	 * (3) The number of rounds to be played in the competition.
	 * @param args an array of string representations of the three input arguments, as detailed above.
	 */
	public static void main(String[] args) {
		int p1Type = parsePlayer1Type(args);
		int p2Type = parsePlayer2Type(args);
		int numGames = parseNumberOfGames(args);
        Scanner scanner = new Scanner(System.in);

        // Create player objects according to the passed in arguments.
        Player player1 = new Player(p1Type, 1, scanner);
        Player player2 = new Player(p2Type, 2, scanner);

        // Check if any of the players is human. In case there's a human the game will be player in verbose mode.
        boolean isHuman = player1.getPlayerType() == Player.HUMAN || player2.getPlayerType() == Player.HUMAN;

        // Create a new instace of Competition object with the given params.
        Competition competition = new Competition(player1, player2, isHuman);

        System.out.println("Starting a Nim competition of " + numGames + " rounds between a " + player1.getTypeName() +
                " player and a " + player2.getTypeName() + " player.");

        // Play the given number of games.
        competition.playMultiple(numGames);

        System.out.println("The results are " + competition.getPlayerScore(1) + ":" + competition.getPlayerScore(2));
	}	
	
}

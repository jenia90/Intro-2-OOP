import java.sql.Time;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * The Competition class represents a Nim competition between two players, consisting of a given number of rounds. 
 * It also keeps track of the number of victories of each player.
 */
public class Competition {
	private static Player mPlayer1;
    private static Player mPlayer2;
    private static  boolean mDisplayMessage;
    private static int p1Wins = 0;
    private static int p2Wins = 0;

	/* You need to implement this class */
	public Competition(Player player1, Player player2, boolean displayMessage){
        mPlayer1 = player1;
        mPlayer2 = player2;
        mDisplayMessage = true;// = displayMessage;
	}

    public static int getPlayerScore(int playerPosition){
        switch (playerPosition){
            case 1:
                return p1Wins;
            case 2:
                return p2Wins;

            default:
                return -1;
        }
    }

    public static void playMultiple(int numRounds){
        for (int i = 0; i < numRounds; i++) {
            Player currentPlayer = mPlayer1;

            Board board = new Board();
            printMessage("Welcome to the sticks game!");


            while(board.getNumberOfUnmarkedSticks() > 0){
                System.out.println(board); // TODO: Delete before production!
                Move currentMove;
                printMessage(String.format("Player %d, it is now your turn!", currentPlayer.getPlayerId()));


                do {
                    currentMove = currentPlayer.produceMove(board);

                    if(board.markStickSequence(currentMove) < 0)
                        printMessage("Invalid move. Enter another:");
                    else {
                        break;
                    }
                } while(true);

                printMessage(String.format("Player %d made the move: %s", currentPlayer.getPlayerId(), currentMove.toString()));

                if(currentPlayer.equals(mPlayer1))
                    currentPlayer = mPlayer2;
                else
                    currentPlayer = mPlayer1;
            }

            System.out.println(String.format("Player %d won!", currentPlayer.getPlayerId()));
            switch (currentPlayer.getPlayerId()){
                case 1:
                    p1Wins++;
                    break;
                case 2:
                    p2Wins++;
                    break;
            }


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

    private static void printMessage(String str){
        if(mDisplayMessage)
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

        mPlayer1 = new Player(p1Type, 1, new Scanner(System.in));
        mPlayer2 = new Player(p2Type, 2, new Scanner(System.in));

        System.out.println(String.format("Starting a Nim competition of %d rounds between a %s player and a %s player.",
                numGames, mPlayer1.getTypeName(), mPlayer2.getTypeName()));

        long startTime = System.currentTimeMillis(); // TODO: Delete before production
        playMultiple(numGames);
        System.out.println(String.format("Player 1 score: %d\tPlayer 2 score: %d", getPlayerScore(1), getPlayerScore(2)));
        System.out.println(String.format("Runtime: %d",System.currentTimeMillis() - startTime)); // TODO: Delete before production
	}	
	
}

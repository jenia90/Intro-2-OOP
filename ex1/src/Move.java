/**
 * The Move class represents a move in the Nim game by a player. A move consists of the row on which it is applied,
 * the left bound (inclusive) of the sequence of sticks to mark, and the right bound (inclusive) of the same sequence.
 */
public class Move {

    private final int mInRow;
    private final int mInLeft;
    private final int mInRight;

    /**
     * Constructs a Move object with the given parameters
     * @param inRow The row on which the move is performed.
     * @param inLeft The left bound of the sequence to mark.
     * @param inRight The right bound of the sequence to mark.
     */
    public Move(int inRow,int inLeft,int inRight) {
        mInRow = inRow;
        mInLeft = inLeft;
        mInRight = inRight;
    }

    /**
     * @return The row on which the move is performed.
     */
    public int getRow(){
        return mInRow;
    }

    /**
     * @return The left bound of the stick sequence to mark.
     */
    public int getLeftBound(){
        return mInLeft;
    }

    /**
     * @return The right bound of the stick sequence to mark.
     */
    public int getRightBound(){
        return mInRight;
    }

    /**
     * a string representation of the move. For example, if the row is 2,
     * the left bound of the sequence is 3 and the right bound is 5, this function will return the string "2:3-5" (without any spaces).
     * @return a string representation of the move.
     */
    @Override
    public String toString() {
        return String.format("%d:%d-%d", mInRow, mInLeft, mInRight);
    }
}

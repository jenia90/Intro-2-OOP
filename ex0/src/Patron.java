/**
 * This class represents a library patron that has a name and assigns values to different literary aspects of books.
 * @author jenia90
 */
public class Patron {
    private final String patronFirstName;
    private final String patronLastName;
    private final int comicTendency;
    private final int dramaticTendency;
    private final int educationalTendency;
    private final int patronEnjoymentThreshold;
    private int numberOfBorrowedBooks;

    /**
     * Constructor
     * Creates a new patron with the given characteristics.
     * @param patronFirstName The first name of the patron
     * @param patronLastName The last name of the patron.
     * @param comicTendency The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency The weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency The weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron to enjoy it.
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
                  int educationalTendency, int patronEnjoymentThreshold) {
        this.patronFirstName = patronFirstName;
        this.patronLastName = patronLastName;
        this.comicTendency = comicTendency;
        this.dramaticTendency = dramaticTendency;
        this.educationalTendency = educationalTendency;
        this.patronEnjoymentThreshold = patronEnjoymentThreshold;
        this.numberOfBorrowedBooks = 0;
    }

    /**
     * Returns a string representation of the patron, which is a sequence of its first and last name, separated by
     * single white space. For example, if the patron's first name is "Ricky" and his late name is "Bobby",
     * this method will return the String "Ricky Bobby".
     * @return the String representation of this patron
     */
    public String stringRepresentation(){
        return String.format("%s %s", this.patronFirstName, this.patronLastName);
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     * @param book The book to asses.
     * @return the literarry value this patron assigns to the given book.
     */
    public int getBookScore(Book book){
        return book.getLiteraryValue();
    }

    /**
     * Returns true if this patron will enjoy the given book, false otherwise.
     * @param book The book to asses.
     * @return true if this patron will enjoy the given book, false otherwise.
     */
    public boolean willEnjoyBook(Book book){
        return book.getLiteraryValue() >= this.patronEnjoymentThreshold;
    }

    /**
     * Returns the number of books this patron currently borrowing
     * @return the int number of books this patron currently borrowing
     */
    public int getNumberOfBorrowedBooks(){
        return this.numberOfBorrowedBooks;
    }
}

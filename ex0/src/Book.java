/**
 * Represents a book
 * @author jenia90
 */
public class Book{
    /**
     * Default borrowerId for when the book isn't borrowed
     */
    private static final int RETURNED_BORROWER_ID = -1;

    private final String bookTitle;
    private final String bookAuthor;
    private final int bookYearOfPublication;
    private int bookComicValue;
    private int bookDramaticValue;
    private int bookEducationalValue;
    private int borrowerId;

    /**
     * Constructor.
     * Creates a new book with the given characteristics.
     * @param bookTitle The title of the book.
     * @param bookAuthor The name of the author of the book.
     * @param bookYearOfPublication The year the book was published.
     * @param bookComicValue The comic value of the book.
     * @param bookDramaticValue The dramatic value of the book.
     * @param bookEducationalValue The educational value of the book.
     */
    Book(String bookTitle, String bookAuthor, int bookYearOfPublication,
         int bookComicValue, int bookDramaticValue, int bookEducationalValue){

        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookYearOfPublication = bookYearOfPublication;
        this.bookComicValue = bookComicValue;
        this.bookDramaticValue = bookDramaticValue;
        this.bookEducationalValue = bookEducationalValue;
    }

    /**
     * Gets the borrower's ID number
     * @return the id of the current borrower of this book
     */
    public int getCurrentBorrowerId() {
        return this.borrowerId;
    }

    /**
     * Calculates the literary value
     * @return the literary value of tis book, which is defined as the sum of its comic, dramatic and educational values.
     */
    public int getLiteraryValue(){
        return this.bookComicValue + this.bookEducationalValue + this.bookDramaticValue;
    }

    /**
     * Marks this book as returned.
     */
    public void returnBook(){
        this.borrowerId = RETURNED_BORROWER_ID;
    }

    /**
     * Sets the borrower ID
     * @param borrowerId borrower's ID number
     */
    public void setBorrowerId(int borrowerId){
        this.borrowerId = borrowerId;
    }

    /**
     * Returns the string representation of the book, which is a sequence of the title, author, year of publication and
     * the total literary value of the book, separated by commes, enclosed by square brackets.
     * For example, if the book is titled "Monkey Feet", was written by Ernie Douglas, published in 1987 and has a
     * comic value of 7, dramatic value of 3 and an educational value of 1, this method will return the string:
     * "[Monkey Feet,Ernie Douglas,1987,11]"
     * @return the String representation of this book.
     */
    public String stringRepresentation(){
        return String.format("[%s,%s,%d,%d]", this.bookTitle, this.bookAuthor, this.bookYearOfPublication, getLiteraryValue());
    }
}

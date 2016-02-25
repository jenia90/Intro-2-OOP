/**
 * This class represents a book, which has a title, author, year of publication and different literary aspects
 * @author jenia90
 */
public class Book{
    /**
     * Default borrowerId for when the book isn't borrowed
     */
    private final String bookTitle;
    private final String bookAuthor;
    private final int bookYearOfPublication;
    private int bookComicValue;
    private int bookDramaticValue;
    private int bookEducationalValue;
    private int borrowerId;

    /**
     * Constructor.
     * Creates a new book with the given characteristic.
     * @param bookTitle The title of the book.
     * @param bookAuthor The name of the author of the book.
     * @param bookYearOfPublication The year the book was published.
     * @param bookComicValue The comic value of the book.
     * @param bookDramaticValue The dramatic value of the book.
     * @param bookEducationalValue The educational value of the book.
     */
    public Book(String bookTitle, String bookAuthor, int bookYearOfPublication,
         int bookComicValue, int bookDramaticValue, int bookEducationalValue){

        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookYearOfPublication = bookYearOfPublication;
        this.bookComicValue = bookComicValue;
        this.bookDramaticValue = bookDramaticValue;
        this.bookEducationalValue = bookEducationalValue;
        borrowerId = -1;
    }

    /**
     * Gets the borrower's ID number
     * @return the id of the current borrower of this book
     */
    public int getCurrentBorrowerId() {
        return borrowerId;
    }

    /**
     * Calculates the literary value
     * @return the literary value of this book, which is defined as the sum of its comic value, its dramatic value and its educational value.
     */
    public int getLiteraryValue(){
        return bookComicValue + bookEducationalValue + bookDramaticValue;
    }

    /**
     * Returns the book's comic value.
     * @return int of book's comic value.
     */
    public int getBookComicValue(){
        return bookComicValue;
    }

    /**
     * Returns the book's dramatic value.
     * @return int of book's dramatic value.
     */
    public int getBookDramaticValue(){
        return bookDramaticValue;
    }

    /**
     * Returns the book's educational value.
     * @return int of book's educational value.
     */
    public int getBookEducationalValue(){
        return bookEducationalValue;
    }

    /**
     * Marks this book as returned.
     */
    public void returnBook(){
        borrowerId = -1;
    }

    /**
     * Sets the given id as the id of the current borrower of this book, -1 if no patron is currently borrowing it.
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
        return String.format("[%s,%s,%d,%d]", bookTitle, bookAuthor, bookYearOfPublication, getLiteraryValue());
    }
}

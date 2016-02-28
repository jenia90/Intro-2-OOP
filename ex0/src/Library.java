/**
 * This class represents a library, which holds a collection of books. Patrons can register at the library to be able
 * to check out books, if a copy of the requested book is available.
 * @author jenia90
 */
public class Library {
    private static final int DEFAULT_ID = -1;

    private final int maxBookCapacity;
    private final int maxBorrowedBooks;
    private final int maxPatronCapacity;
    private Book[] books;
    private Patron[] patrons;
    private int booksCount;
    private int patronsCount;

    /**
     * Creates new library with the given characteristics.
     * @param maxBookCapacity The maximal number of books this library can hold.
     * @param maxBorrowedBooks The maximal number of books this library allows a single patron to borrow at the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    public Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        this.maxBookCapacity = maxBookCapacity;
        this.maxBorrowedBooks = maxBorrowedBooks;
        this.maxPatronCapacity = maxPatronCapacity;
        books = new Book[maxBookCapacity];
        patrons = new Patron[maxPatronCapacity];
        booksCount = 0;
        patronsCount = 0;
    }

    /**
     * Adds the given book to this library, if there is a place available, and it isn't already in the library.
     * @param book The book to add to the library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    public int addBookToLibrary(Book book) {
        int bookId = getBookId(book);

        if(isBookIdValid(bookId))
            return bookId;

        else if(booksCount < maxBookCapacity){
            books[booksCount] = book;
            return booksCount++;
        } else
            return DEFAULT_ID;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    public boolean isBookIdValid(int bookId) {
        if (bookId >= 0 && bookId < booksCount)
            return books[bookId] != null;
        return false;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    public int getBookId(Book book){
        for (int b = 0; b < booksCount; b++) {
            if(books[b] == book)
                return b;
        }
        return DEFAULT_ID;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    public boolean isBookAvailable(int bookId) {
        if(isBookIdValid(bookId))
            return books[bookId].getCurrentBorrowerId() == DEFAULT_ID;
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully registered, a negative number otherwise.
     */
    public int registerPatronToLibrary(Patron patron){
        if(patronsCount < maxPatronCapacity) {
            patrons[patronsCount] = patron;
            return patronsCount++;
        } else
            return DEFAULT_ID;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    public boolean isPatronIdValid(int patronId){
        if (patronId >= 0 && patronId < patronsCount)
            return patrons[patronId] != null;
        return false;
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    public int getPatronId(Patron patron){
        for (int p = 0; p < maxPatronCapacity; p++) {
            if(patrons[p] == patron)
                return p;
        }

        return DEFAULT_ID;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this book is available,
     * the given patron isn't already borrowing the maximal number of books allowed, and if the patron will enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    public boolean borrowBook(int bookId, int patronId){
        if (!isBookIdValid(bookId) || !isPatronIdValid(patronId))
            return false;

        Book book = books[bookId];
        Patron patron = patrons[patronId];

        if(patron.getNumberOfBorrowedBooks() < maxBorrowedBooks && patron.willEnjoyBook(book) && isBookAvailable(bookId)) {
            book.setBorrowerId(patronId);
            patron.increaseNumberOfBorrowedBooks();
            return true;
        }

        return false;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    public void returnBook(int bookId){
        if(!isBookIdValid(bookId))
            return;

        Book book = books[bookId];

        patrons[book.getCurrentBorrowerId()].decreaseNumberOfBorrowedBooks();
        book.returnBook();
    }

    public Book suggestBookToPatron(int patronId){
        if(!isPatronIdValid(patronId))
            return null;

        int threshold = 0;
        Book suggestedBook = null;
        Patron patron = patrons[patronId];

        for (int b = 0; b < booksCount; b++) {
            if (patron.willEnjoyBook(books[b]) && books[b].getLiteraryValue() > threshold) {
                suggestedBook = books[b];
            }
        }

        return suggestedBook;
    }
}

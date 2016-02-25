import java.util.List;

/**
 * This class represents a library, which holds a collection of books. Patrons can register at the library to be able
 * to check out books, if a copy of the requested book is available.
 * @author jenia90
 */
public class Library {
    private final int maxBookCapacity;
    private final int maxBorrowedBooks;
    private final int maxPatronCapacity;
    private List<Book> libraryBooks;
    private List<Patron> libraryPatrons;

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
    }

    /**
     * Adds the given book to this library, if thr is a place available, and it isn't already in the library.
     * @param book The book to add to the library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    public int addBookToLibrary(Book book){
        if (libraryBooks.contains(book) || libraryBooks.size() >= maxBookCapacity)
            return -1;
        else {
            libraryBooks.add(book);
            return libraryBooks.size();
        }
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    public boolean isBookIdValid(int bookId){
        return bookId <= libraryBooks.size();
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    public int getBookId(Book book){
        return libraryBooks.contains(book) ? libraryBooks.indexOf(book) : -1;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    public boolean isBookAvailable(int bookId){
        return libraryBooks.get(bookId).getCurrentBorrowerId() < 0;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully registered, a negative number otherwise.
     */
    public int registrPatronToLibrary(Patron patron){
        if(libraryPatrons.size() <= maxPatronCapacity){
            libraryPatrons.add(patron);
            return libraryPatrons.size();
        }
        else
            return -1;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    public boolean isPatronIdValid(int patronId){
        return patronId <= libraryPatrons.size();
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    public int getPatronId(Patron patron){
        return libraryPatrons.contains(patron) ? libraryPatrons.indexOf(patron) : -1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this book is available,
     * the given patron isn't already borrowing the maximal number of books allowed, and if the patron will enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    public boolean borrowBook(int bookId, int patronId){
        Book book = libraryBooks.get(bookId);
        Patron patron = libraryPatrons.get(patronId);

        if(isBookAvailable(bookId) && patron.getNumberOfBorrowedBooks() < maxBorrowedBooks && patron.willEnjoyBook(book)) {
            book.setBorrowerId(patronId);
            return true;
        }
        else
            return false;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    public void returnBook(int bookId){
        libraryBooks.get(bookId).returnBook();
    }

    public Book suggestBookToPatron(int patronId){
        Patron patron = libraryPatrons.get(patronId);

        int threshold = 0;
        Book suggestedBook = null;

        for (Book book : libraryBooks) {
            if(patron.willEnjoyBook(book) && book.getLiteraryValue() > threshold){
                suggestedBook = book;
            }
        }

        return suggestedBook;
    }
}

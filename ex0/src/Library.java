/**
 * This class represents a library, which holds a collection of books. Patrons can register at the library to be able
 * to check out books, if a copy of the requested book is available.
 * @author jenia90
 */
class Library {
    static final int DEFAULT_VAL = -1;

    final int maxBookAmount;
    final int maxBorrowedBooksAmount;
    final int maxPatronAmount;
    Book[] books;
    Patron[] patrons;
    int booksCount;
    int patronsCount;

    /**
     * Creates new library with the given characteristics.
     * @param maxBookCapacity The maximal number of books this library can hold.
     * @param maxBorrowedBooks The maximal number of books this library allows a single patron to borrow at the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        maxBookAmount = maxBookCapacity;
        maxBorrowedBooksAmount = maxBorrowedBooks;
        maxPatronAmount = maxPatronCapacity;
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
    int addBookToLibrary(Book book) {
        int bookId = getBookId(book);

        if(isBookIdValid(bookId))
            return bookId;

        else if(booksCount < maxBookAmount){
            books[booksCount] = book;
            return booksCount++;
        } else
            return DEFAULT_VAL;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book){
        for (int b = 0; b < booksCount; b++) {
            if(books[b].equals(book))
                return b;
        }
        return DEFAULT_VAL;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        if (bookId >= 0 && bookId < booksCount)
            return books[bookId] != null;
        return false;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if(isBookIdValid(bookId))
            return books[bookId].getCurrentBorrowerId() == DEFAULT_VAL;
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully registered, a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron){
        int patronId = getPatronId(patron);

        if(isPatronIdValid(patronId))
            return patronId;

        else if(patronsCount < maxPatronAmount) {
            patrons[patronsCount] = patron;
            return patronsCount++;
        } else
            return DEFAULT_VAL;
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron){
        for (int p = 0; p < patronsCount; p++) {
            if(patrons[p].equals(patron))
                return p;
        }
        return DEFAULT_VAL;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId){
        if (patronId >= 0 && patronId < patronsCount)
            return patrons[patronId] != null;
        return false;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this book is available,
     * the given patron isn't already borrowing the maximal number of books allowed, and if the patron will enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId){
        if(isBookAvailable(bookId) && isBookIdValid(bookId) && isPatronIdValid(patronId)) {
            Book book = books[bookId];
            Patron patron = patrons[patronId];

            if (patron.getNumberOfBorrowedBooks() <= maxBorrowedBooksAmount && patron.willEnjoyBook(book)) {
                book.setBorrowerId(patronId);
                patron.increaseNumberOfBorrowedBooks();
                return true;
            }
        }
        return false;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId){
        if(!isBookIdValid(bookId))
            return;

        Book book = books[bookId];

        patrons[book.getCurrentBorrowerId()].decreaseNumberOfBorrowedBooks();
        book.returnBook();
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he will enjoy, if any such exist.
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given will enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId){
        if(!isPatronIdValid(patronId))
            return null;

        int threshold = 0;
        Book suggestedBook = null;
        Patron patron = patrons[patronId];

        for (int b = 0; b < booksCount; b++) {
            if (patron.willEnjoyBook(books[b]) && books[b].getLiteraryValue() > threshold) {
                suggestedBook = books[b];
                threshold = books[b].getLiteraryValue();
            }
        }

        return suggestedBook;
    }
}

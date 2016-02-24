public class Book {
    String _bookTitle;
    String _bookAuthor;
    int _bookYearOfPublication;
    int _bookComicValue;
    int _bookDramaticValue;
    int _bookEducationalValue;

    Book(String bookTitle, String bookAuthor, int bookYearOfPublication,
         int bookComicValue, int bookDramaticValue, int bookEducationalValue){

        _bookTitle = bookTitle;
        _bookAuthor = bookAuthor;
        _bookYearOfPublication = bookYearOfPublication;
        _bookComicValue = bookComicValue;
        _bookDramaticValue = bookDramaticValue;
        _bookEducationalValue = bookEducationalValue;
    }

    private int getCurrentBorrowerId(){
        return 0;
    }

    private int getLiteraryValue(){
        return 0;
    }

    private void returnBook(){

    }

    private void setBorrowerId(int borrowerId){

    }

    private String stringRepresentation(){
        return String.format("[%s,%s,%d]", _bookTitle, _bookAuthor, getLiteraryValue());
    }
}

package library.library;

import library.classes.books.Book;
import library.classes.library.Library;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class LibraryTest {
    private Book book = Mockito.mock(Book.class);
    private List<Book> booksMoc = Arrays.asList(book, book, book);

    @Test
    public void libraryTest(){
        Library library = new Library(booksMoc);
        Assertions.assertEquals(3, library.getBooksCount());
    }

}

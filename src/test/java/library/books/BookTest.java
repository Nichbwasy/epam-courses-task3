package library.books;

import library.classes.books.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class BookTest {
    @Test
    public void bookTest() {
        Book book = new Book(1L);
        Date curTime = new Date();
        Long time = curTime.getTime() + 1500;
        book.setExpirationTime(time);

        Assertions.assertEquals(1, book.getBookId());
        Assertions.assertEquals(time, book.getExpirationTime());
    }
}

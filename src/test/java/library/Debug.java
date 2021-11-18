package library;

import library.classes.books.Book;
import library.classes.library.Library;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Debug {
    @Test
    public void DebugTest() {
        List<Book> books = new ArrayList<>();
        for(Long i = 1L; i < 20; i++) books.add(new Book(i));
        Library library = new Library(books);
        library.start();
        while (library.isAlive()) {

        }
    }
}

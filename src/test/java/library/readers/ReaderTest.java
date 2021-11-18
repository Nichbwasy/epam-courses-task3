package library.readers;

import library.classes.books.Book;
import library.classes.readers.Reader;
import library.classes.rooms.ReadingRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ReaderTest {
    private ReadingRoom roomMoc = Mockito.mock(ReadingRoom.class);
    private Book book = Mockito.mock(Book.class);
    private List<Book> booksMoc = Arrays.asList(book, book, book);
    private Exchanger<List<Book>> exchanger = Mockito.mock(Exchanger.class);

    @Test
    public void readerCreationTest(){
        Reader reader = new Reader(1L, roomMoc, booksMoc, exchanger);

        Assertions.assertEquals(1L, reader.getUserId());
        Assertions.assertEquals(3, reader.returnBooks().size());
    }

    @Test
    public void runReaderTest() throws InterruptedException {
        Reader reader = new Reader(2L, roomMoc, booksMoc, exchanger);
        Long startTime = new Date().getTime();

        reader.start();
        reader.join();

        Long endTime = new Date().getTime();
        Assertions.assertEquals(true, startTime + 20L < endTime);
        Assertions.assertEquals(false, reader.isAlive());
    }
}

package library.classes.readers;

import library.classes.books.Book;
import library.classes.library.Library;
import library.classes.rooms.ReadingRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Reader extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Reader.class);

    private Long readerId;
    private List<Book> books;
    private ReadingRoom readingRoom;

    public Reader(Long id, ReadingRoom readingRoom, List<Book> books) {
        this.readerId = id;
        this.readingRoom = readingRoom;
        this.books = books;
    }

    public List<Book> returnBooks() {
        return books;
    }

    public Long getUserId() {
        return readerId;
    }

    @Override
    public void run() {
        waiting(20L);
        while (canBeExchanged()) {
            readBooks(15L);
            LOGGER.info("Reader[{}] now is waiting other reader to exchange books.", readerId);
            exchangeBooks(); //!!!
        }
        readingRoom.leave(this);
    }

    private boolean canBeExchanged() {
        Date curTime = new Date();
        return books.stream().anyMatch(book -> book.getExpirationTime() > curTime.getTime());
    }

    private void readBooks(Long delay) {
        LOGGER.info("Reader[{}] now is reading books.", readerId);
        books.forEach(b -> waiting(delay));
    }

    private void exchangeBooks() {
        try {
            Exchanger<List<Book>> exchanger = new Exchanger<>();
            List<Book> exBooks = exchanger.exchange(books, 150, TimeUnit.MILLISECONDS);
            if (exBooks != null) {
                LOGGER.info("Reader[{}] now is exchanging books.", readerId);
                books = exBooks;
            } else {
                LOGGER.info("Reader[{}] hasn't exchanged books.", readerId);
            }
        } catch (InterruptedException | TimeoutException e) {
            LOGGER.warn("Reader[{}] got tired of waiting and left! ", readerId);
        }
    }

    private void waiting(Long delay) {
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            LOGGER.error("Reader can't prepare! ", e);
            e.printStackTrace();
        }
    }

}

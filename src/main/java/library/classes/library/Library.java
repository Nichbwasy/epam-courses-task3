package library.classes.library;

import library.classes.books.Book;
import library.classes.readers.Reader;
import library.classes.rooms.ReadingRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Library extends Thread{
    private static final Logger LOGGER = LoggerFactory.getLogger(Library.class);

    private Long usersCount = 0L;
    private Lock lock = new ReentrantLock();
    private List<Book> books;
    private ReadingRoom readingRoom;
    private Exchanger<List<Book>> exchanger = new Exchanger<>();

    public Library(List<Book> books) {
        Thread.currentThread().setName("Library");
        readingRoom = new ReadingRoom(this);
        this.books = books;
    }

    public Integer getBooksCount(){
        return books.size();
    }

    public void returnBooks(List<Book> books) {
        lock.lock();
        this.books.addAll(books);
        lock.unlock();
    }


    @Override
    public void run() {
        Integer needBooks;
        while (true) {
            needBooks = (int)(Math.random() * 10 + 1);
            LOGGER.info("New reader came to the library and waiting {} books.", needBooks);
            while(books.size() < needBooks) {
                waitingDelay(10L);
            }
            enterReader(needBooks);
        }
    }

    /**
     * Returns necessary count of books from library
     * @param count Necessary books count
     * @return Books from the library
     */
    private List<Book> getBooks(Integer count){
        lock.lock();
        List<Book> retBooks = new ArrayList<>();
        for (int i = 0; i < count; i++){
            Date curTime = new Date();
            Book book = books.get(0);
            book.setExpirationTime(curTime.getTime() + 50);
            retBooks.add(book);
            books.remove(0);
        }
        LOGGER.info("The {} gave {} books to reader. Books left {}", Thread.currentThread().getName(), count, books.size());
        lock.unlock();
        return retBooks;
    }

    /**
     * Add and starts new reader into the reading room
     * @param needBooks Count of books which reader needs
     */
    private void enterReader(Integer needBooks) {
        List<Book> retBooks = getBooks(needBooks);
        lock.lock();
        usersCount++;
        lock.unlock();
        Reader newReader = new Reader(usersCount, readingRoom, retBooks, exchanger);
        newReader.start();
        readingRoom.enter(newReader);
    }

    private void waitingDelay(Long delay) {
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            LOGGER.error("Library error.", e);
        }
    }
}

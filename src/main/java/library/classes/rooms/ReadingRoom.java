package library.classes.rooms;

import library.classes.books.Book;
import library.classes.library.Library;
import library.classes.readers.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ReadingRoom {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadingRoom.class);

    private Library library;
    private List<Reader> readers = new ArrayList<Reader>();

    public ReadingRoom(Library library) {
        this.library = library;
    }

    public void enter(Reader reader) {
        readers.add(reader);
        LOGGER.info("Reader[{}] has entered into reading room.", reader.getUserId());
    }

    public void leave(Reader reader) {
        List<Book> retBooks = reader.returnBooks();
        library.returnBooks(retBooks);
        readers.remove(reader);
        LOGGER.info("Reader[{}] has left the library. Books returns to the library {}", reader.getUserId(), retBooks.size());
    }

    public Integer getReadersCount(){
        return readers.size();
    }

}

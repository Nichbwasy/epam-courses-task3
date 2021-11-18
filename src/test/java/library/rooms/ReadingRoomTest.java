package library.rooms;

import library.classes.library.Library;
import library.classes.readers.Reader;
import library.classes.rooms.ReadingRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ReadingRoomTest {
    Library libraryMoc = Mockito.mock(Library.class);
    Reader reader = Mockito.mock(Reader.class);

    @Test
    public void ReadingRoomTest(){
        ReadingRoom readingRoom = new ReadingRoom(libraryMoc);

        Assertions.assertEquals(0, readingRoom.getReadersCount());
        readingRoom.enter(reader);
        Assertions.assertEquals(1, readingRoom.getReadersCount());
        readingRoom.leave(reader);
        Assertions.assertEquals(0, readingRoom.getReadersCount());
    }
}

package notisblokk.json;

import java.time.LocalDateTime;
import notisblokk.core.Note;
import org.junit.Before;
import org.junit.Test;

public class NoteDeserializerTest {

  NoteDeserializer noteDeserializer = new NoteDeserializer();

  @Before
  public void init(){

  }

  @Test
  public void testDeserializeNoteFromString(){
    LocalDateTime now = LocalDateTime.now();
    Note note = new Note("TEST TITLE", "TEST MESSAGE", now, now);
    System.out.println(note.toString());
  }

}

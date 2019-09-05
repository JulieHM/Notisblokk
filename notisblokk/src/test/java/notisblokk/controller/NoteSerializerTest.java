package notisblokk.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import notisblokk.model.Note;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoteSerializerTest {

  private NoteSerializer noteSerializer = new NoteSerializer();
  private LocalDateTime time;
  private List<Note> noteList = new ArrayList<>();
  private String completePath = System.getenv("LOCALAPPDATA") + "\\projectNotes\\notes.json";

  @Before
  public void init() {
    time = LocalDateTime.of(2050, 2, 20, 4, 50, 43);
    noteList.add(new Note("title1", "msg1", time, time));
    noteList.add(new Note("title2", "msg2", time, time));
    noteList.add(new Note("title3", "msg3", time, time));
  }

  @Test
  public void testSerializeNotes() throws IOException {
    boolean regularSerialization = noteSerializer.serializeNotes(noteList, completePath);
    Assert.assertTrue(regularSerialization);

    boolean emptySerialization = noteSerializer.serializeNotes(new ArrayList<Note>(), completePath);
    Assert.assertTrue(emptySerialization);
  }

}

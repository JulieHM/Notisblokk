package notisblokk.json;

import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import notisblokk.core.Note;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoteSerializerTest {

  private Gson serializer = new Gson();
  private NoteSerializer noteSerializer = new NoteSerializer();
  private LocalDateTime time;
  private List<Note> noteList = new ArrayList<>();
  private String savePath = System.getProperty("user.home") + "/.projectNotes/notes.json";

  /**
   * Creates test objects before each test is run.
   */
  @Before
  public void init() {
    time = LocalDateTime.of(2050, 2, 20, 4, 50, 43);
    noteList.add(new Note("title1", "msg1", time, time));
    noteList.add(new Note("title2", "msg2", time, time));
    noteList.add(new Note("title3", "msg3", time, time));
  }

  @Test
  public void testSerializeNotes() throws IOException {
    boolean regularSerialization = noteSerializer.serializeNotesToLocal(noteList, savePath);
    Assert.assertTrue(regularSerialization);

    boolean emptySerialization = noteSerializer.serializeNotesToLocal(new ArrayList<>(), savePath);
    Assert.assertTrue(emptySerialization);
  }

  @Test
  public void testSerializeNotesToString() {
    String notesAsString = "[";
    List<Note> notes = new ArrayList<Note>();
    for (int i = 0; i < 5; i++) {
      LocalDateTime now = LocalDateTime.now();
      String nowAsString = serializer.toJson(now);
      Note currentNode = new Note("Note " + i, "Message " + i, now, now);
      notes.add(currentNode);
      if (i < 4) {
        notesAsString +=
            "{\"title\":\"" + currentNode.getTitle() + "\",\"message\":\"" + currentNode
                .getMessage()
                + "\",\"lastEditedDate\":" + nowAsString + ",\"createdDate\":" + nowAsString + "},";
      } else {
        notesAsString +=
            "{\"title\":\"" + currentNode.getTitle() + "\",\"message\":\"" + currentNode
                .getMessage()
                + "\",\"lastEditedDate\":" + nowAsString + ",\"createdDate\":" + nowAsString + "}";
      }
    }
    notesAsString += "]";

    Assert.assertEquals(notesAsString, noteSerializer.serializeNotesToString(notes));
  }

  @Test
  public void testSerializeNoteToString() {
    LocalDateTime now = LocalDateTime.now();
    Note note = new Note("TEST TITLE", "TEST MESSAGE", now, now);
    String nowAsString = serializer.toJson(now);
    String noteAsString =
        "{\"title\":\"" + note.getTitle() + "\",\"message\":\"" + note.getMessage()
            + "\",\"lastEditedDate\":" + nowAsString + ",\"createdDate\":" + nowAsString + "}";
    Assert.assertEquals(noteAsString, noteSerializer.serializeNoteToString(note));
  }

}

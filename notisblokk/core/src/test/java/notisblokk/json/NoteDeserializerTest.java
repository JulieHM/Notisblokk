package notisblokk.json;

import java.time.LocalDateTime;
import java.util.List;
import notisblokk.core.Note;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoteDeserializerTest {

  NoteDeserializer noteDeserializer = new NoteDeserializer();
  private static final String SAVE_PATH = System.getProperty("user.home")
      + "/.projectNotes/notes.json";

  @Before
  public void init() {

  }

  @Test
  public void testDeserializeNotesFromString() {
    String notesAsString =
        "[{\"title\":\"TEST TEST\",\"message\":\"TEST TESTTEST TESTTEST"
            + " TESTTEST TEST\",\"lastEditedDate\":{\"date\":{\"year\":2019,\"month\":9,\"day\":19},\"time\""
            + ":{\"hour\":11,\"minute\":14,\"second\":53,\"nano\":666338700}},\"createdDate\":{\"date\":"
            + "{\"year\":2019,\"month\":9,\"day\":19},\"time\":{\"hour\":11,\"minute\":14,\"second\":49,\""
            + "nano\":764668100}}},{\"title\":\"SomeTitle\",\"message\":\"SomeMessage\",\"lastEditedDate\""
            + ":{\"date\":{\"year\":2019,\"month\":9,\"day\":19},\"time\":{\"hour\":11,\"minute\":14,\"second"
            + "\":53,\"nano\":666338700}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":9,\"day\":19},"
            + "\"time\":{\"hour\":11,\"minute\":14,\"second\":49,\"nano\":764668100}}},{\"title\":\"Third title"
            + "\",\"message\":\"Third message Third message Third message \",\"lastEditedDate\":{\"date\":"
            + "{\"year\":2019,\"month\":9,\"day\":19},\"time\":{\"hour\":11,\"minute\":14,\"second\":53,"
            + "\"nano\":666338700}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":9,\"day\":19},\"time\""
            + ":{\"hour\":11,\"minute\":14,\"second\":49,\"nano\":764668100}}}]";
    List<Note> noteList = noteDeserializer.deserializeNotesFromString(notesAsString);
    LocalDateTime lastEdit = LocalDateTime.of(2019, 9, 19, 11, 14, 53, 666338700);
    LocalDateTime created = LocalDateTime.of(2019, 9, 19, 11, 14, 49, 764668100);
    String[] titles = {"TEST TEST", "SomeTitle", "Third title"};
    String[] messages = {"TEST TESTTEST TESTTEST TESTTEST TEST", "SomeMessage",
        "Third message Third message Third message "};

    for (int i = 0; i < noteList.size(); i++) {
      Assert.assertEquals(titles[i], noteList.get(i).getTitle());
      Assert.assertEquals(messages[i], noteList.get(i).getMessage());
      Assert.assertEquals(lastEdit, noteList.get(i).getLastEditedDate());
      Assert.assertEquals(created, noteList.get(i).getCreatedDate());
    }
  }

  @Test
  public void testDeserializeNoteFromString() {
    String noteAsString =
        "{\"title\":\"TEST TITLE\",\"message\":\"TEST MESSAGE\",\"lastEditedDate\""
            + ":{\"date\":{\"year\":2019,\"month\":9,\"day\":19},\"time\":{\"hour\":11,\"minute\":14,\""
            + "second\":53,\"nano\":666338700}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":9,\""
            + "day\":19},\"time\":{\"hour\":11,\"minute\":14,\"second\":49,\"nano\":764668100}}}";
    Note note = noteDeserializer.deserializeNoteFromString(noteAsString);
    Assert.assertEquals("TEST TITLE", note.getTitle());
    Assert.assertEquals("TEST MESSAGE", note.getMessage());
    Assert.assertEquals(LocalDateTime.of(2019, 9, 19, 11, 14, 53, 666338700),
        note.getLastEditedDate());
    Assert
        .assertEquals(LocalDateTime.of(2019, 9, 19, 11, 14, 49, 764668100), note.getCreatedDate());
  }

}

package notisblokk.json;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import notisblokk.core.Note;
import org.junit.Assert;
import org.junit.Test;

public class NoteDeserializerTest {

  private NoteDeserializer noteDeserializer = new NoteDeserializer();

  /**
   * Tests deserialization of an array of notes into an ArrayList of note-objects.
   */
  @Test
  public void testDeserializeNotesFromString() {
    String notesJson = "["
        + "{\"title\":\"New note 1\",\"message\":\"New note 1\",\"lastEditedDate\":{\"date\":"
        + "{\"year\":2019,\"month\":10,\"day\":3},\"time\":{\"hour\":10,\"minute\":51,\"second"
        + "\":14,\"nano\":663673000}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":10,\""
        + "day\":3},\"time\":{\"hour\":10,\"minute\":51,\"second\":14,\"nano\":663673000}}},"
        + "{\"title\":\"New note 2\",\"message\":\"New note 2\",\"lastEditedDate\":{\"date\":{"
        + "\"year\":2019,\"month\":10,\"day\":3},\"time\":{\"hour\":10,\"minute\":51,\"second\""
        + ":14,\"nano\":663673000}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":10,\"day"
        + "\":3},\"time\":{\"hour\":10,\"minute\":51,\"second\":14,\"nano\":663673000}}},"
        + "{\"title\":\"New note 3\",\"message\":\"New note 3\",\"lastEditedDate\":{\"date\":{\""
        + "year\":2019,\"month\":10,\"day\":3},\"time\":{\"hour\":10,\"minute\":51,\"second\":14,"
        + "\"nano\":663673000}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":10,\"day\":3},"
        + "\"time\":{\"hour\":10,\"minute\":51,\"second\":14,\"nano\":663673000}}}"
        + "]";
    final List<Note> notesFromJson = noteDeserializer.deserializeNotesFromString(notesJson);
    List<Note> notes = new ArrayList<>();
    LocalDateTime time = LocalDateTime.of(2019, 10, 3, 10, 51, 14, 663673000);
    notes.add(new Note("New note 1", "New note 1", time, time));
    notes.add(new Note("New note 2", "New note 2", time, time));
    notes.add(new Note("New note 3", "New note 3", time, time));
    Assert.assertEquals(notes, notesFromJson);
  }

  /**
   * Tests deserialization of a single note into a note object.
   */
  @Test
  public void testDeserializeNoteFromString() {
    String noteString = "{\"title\":\"New note 1\",\"message\":\"New note 1\",\"lastEditedDate\":"
        + "{\"date\":{\"year\":2019,\"month\":10,\"day\":3},\"time\":{\"hour\":10,\"minute\":51,\""
        + "second\":14,\"nano\":663673000}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":10,"
        + "\"day\":3},\"time\":{\"hour\":10,\"minute\":51,\"second\":14,\"nano\":663673000}}}";
    LocalDateTime time = LocalDateTime.of(2019, 10, 3, 10, 51, 14, 663673000);
    Note note = new Note("New note 1", "New note 1", time, time);
    Note noteDeserialized = noteDeserializer.deserializeNoteFromString(noteString);
    Assert.assertEquals(note, noteDeserialized);
  }
}

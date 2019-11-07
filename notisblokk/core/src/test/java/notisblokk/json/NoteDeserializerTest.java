package notisblokk.json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import notisblokk.core.Category;
import notisblokk.core.Note;
import notisblokk.core.Notebook;
import org.junit.Assert;
import org.junit.Test;

public class NoteDeserializerTest {

  private static final String TEST_PATH = System.getProperty("user.home")
      + "/it1901/tests/testNotes.json";

  private static final String GIBBERISH_PATH = System.getProperty("user.home")
      + "/it1901it1901it1901it1901/notisblokknotisblokknotisblokknotisblokknotisblokk"
      + "/notesnotesnotesnotes.json";

  private NoteDeserializer noteDeserializer = new NoteDeserializer();
  private NoteSerializer noteSerializer = new NoteSerializer();

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

  @Test
  public void testDeserializeLocalNotebookWithoutLocal() throws IOException {
    Notebook emptyNotebook = noteDeserializer.deserializeLocalNotebook(GIBBERISH_PATH);
    Assert.assertEquals(new Notebook().getCategories(), emptyNotebook.getCategories());
    Assert.assertEquals(Notebook.class, emptyNotebook.getClass());
  }

  @Test
  public void testDeserializeLocalNotebook() throws IOException {
    Notebook notebook = setupCorrectNotebook();

    noteSerializer.serializeNotebookToLocal(notebook, TEST_PATH);

    Notebook deserializedNotebook = noteDeserializer.deserializeLocalNotebook(TEST_PATH);

    Assert.assertEquals(Notebook.class, deserializedNotebook.getClass());

    for (int i = 0; i < deserializedNotebook.getCategories().size(); i++) {
      // Test name
      Assert.assertEquals(notebook.getCategory(i).getName(),
          deserializedNotebook.getCategory(i).getName());
      // Num notes
      Assert.assertEquals(notebook.getCategory(i).getNumNotes(),
          deserializedNotebook.getCategory(i).getNumNotes());

      for (int y = 0; y < deserializedNotebook.getCategory(i).getNotes().size(); y++) {
        // Title check
        Assert.assertEquals(notebook.getCategory(i).getNote(y).getTitle(),
            deserializedNotebook.getCategory(i).getNote(y).getTitle());
        // Message check
        Assert.assertEquals(notebook.getCategory(i).getNote(y).getMessage(),
            deserializedNotebook.getCategory(i).getNote(y).getMessage());
        // Created date check
        Assert.assertEquals(notebook.getCategory(i).getNote(y).getCreatedDate(),
            deserializedNotebook.getCategory(i).getNote(y).getCreatedDate());
        // Last edited date check
        Assert.assertEquals(notebook.getCategory(i).getNote(y).getLastEditedDate(),
            deserializedNotebook.getCategory(i).getNote(y).getLastEditedDate());

      }
    }
  }

  @Test
  public void testDeserializeFaultyNotebook() throws IOException {
    List<Note> notes = setupFaultyNotebook();

    noteSerializer.serializeNotesToLocal(notes, TEST_PATH);

    Notebook notebook = noteDeserializer.deserializeLocalNotebook(TEST_PATH);

    Assert.assertEquals(notes.get(0), notebook.getCategory(0).getNote(0));
    Assert.assertEquals(notes.get(1), notebook.getCategory(0).getNote(1));
  }

  private Notebook setupCorrectNotebook() {
    Note note1 = new Note("Note 1", "Note 1");
    Note note2 = new Note("Note 2", "Note 2");
    Note note3 = new Note("Note 3", "Note 3");
    Note note4 = new Note("Note 4", "Note 4");
    Category category1 = new Category(note1, note2);
    category1.setName("Category 1");
    Category category2 = new Category(note3, note4);
    category1.setName("Category 2");

    Notebook notebook = new Notebook();
    notebook.addCategory(category1);
    notebook.addCategory(category2);

    return notebook;
  }

  private List<Note> setupFaultyNotebook() {
    List<Note> notes = new ArrayList<>();
    notes.add(new Note("Note 1", "Note 1"));
    notes.add(new Note("Note 2", "Note 2"));
    return notes;
  }
}
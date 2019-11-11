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

  @Test
  public void testDeserializeFaultyNotesFromString() {
    Note fallBackNote = new Note("Empty note", "");
    String notesAsString = "{This is a note[]}";
    List<Note> notes = noteDeserializer.deserializeNotesFromString(notesAsString);
    Assert.assertEquals(fallBackNote.getTitle(), notes.get(0).getTitle());
    Assert.assertEquals(fallBackNote.getMessage(), notes.get(0).getMessage());
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

  @Test
  public void testDeserializeCategoryFromString() {
    Category category = new Category("New category");

    String categoryAsString = noteSerializer.serializeCategoryToString(category);

    Category deserializedCategory = noteDeserializer
        .deserializeCategoryFromString(categoryAsString);

    Assert.assertEquals(category.getName(), deserializedCategory.getName());
    Assert.assertEquals(0, deserializedCategory.getNumNotes());
  }

  @Test
  public void testDeserializeCategoriesFromString() {
    String categoriesAsString = "[\n"
        + "  {\n"
        + "    \"notes\": [\n"
        + "      {\n"
        + "        \"title\": \"Empty note\",\n"
        + "        \"message\": \"\\u003chtml dir\\u003d\\\"ltr\\\"\\u003e\\u003chead\\u003e\\u003c/head\\u003e\\u003cbody contenteditable\\u003d\\\"true\\\"\\u003e\\u003cp\\u003eqeweq2 wer ewf\\u0026nbsp;\\u003c/p\\u003e\\u003c/body\\u003e\\u003c/html\\u003e\",\n"
        + "        \"lastEditedDate\": {\n"
        + "          \"date\": {\n"
        + "            \"year\": 2019,\n"
        + "            \"month\": 11,\n"
        + "            \"day\": 6\n"
        + "          },\n"
        + "          \"time\": {\n"
        + "            \"hour\": 16,\n"
        + "            \"minute\": 39,\n"
        + "            \"second\": 36,\n"
        + "            \"nano\": 211704100\n"
        + "          }\n"
        + "        },\n"
        + "        \"createdDate\": {\n"
        + "          \"date\": {\n"
        + "            \"year\": 2019,\n"
        + "            \"month\": 11,\n"
        + "            \"day\": 6\n"
        + "          },\n"
        + "          \"time\": {\n"
        + "            \"hour\": 16,\n"
        + "            \"minute\": 39,\n"
        + "            \"second\": 18,\n"
        + "            \"nano\": 161582700\n"
        + "          }\n"
        + "        }\n"
        + "      },\n"
        + "      {\n"
        + "        \"title\": \"New note\",\n"
        + "        \"message\": \"\\u003chtml dir\\u003d\\\"ltr\\\"\\u003e\\u003chead\\u003e\\u003c/head\\u003e\\u003cbody contenteditable\\u003d\\\"true\\\"\\u003e\\u003cp\\u003ewe werwe rw\\u003c/p\\u003e\\u003cp\\u003eer wero we\\u003c/p\\u003e\\u003cp\\u003erowero w\\u003c/p\\u003e\\u003c/body\\u003e\\u003c/html\\u003e\",\n"
        + "        \"lastEditedDate\": {\n"
        + "          \"date\": {\n"
        + "            \"year\": 2019,\n"
        + "            \"month\": 11,\n"
        + "            \"day\": 6\n"
        + "          },\n"
        + "          \"time\": {\n"
        + "            \"hour\": 16,\n"
        + "            \"minute\": 39,\n"
        + "            \"second\": 44,\n"
        + "            \"nano\": 842301300\n"
        + "          }\n"
        + "        },\n"
        + "        \"createdDate\": {\n"
        + "          \"date\": {\n"
        + "            \"year\": 2019,\n"
        + "            \"month\": 11,\n"
        + "            \"day\": 6\n"
        + "          },\n"
        + "          \"time\": {\n"
        + "            \"hour\": 16,\n"
        + "            \"minute\": 39,\n"
        + "            \"second\": 37,\n"
        + "            \"nano\": 673753800\n"
        + "          }\n"
        + "        }\n"
        + "      }\n"
        + "    ],\n"
        + "    \"name\": \"qweqwe\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"notes\": [\n"
        + "      {\n"
        + "        \"title\": \"Empty note\",\n"
        + "        \"message\": \"\",\n"
        + "        \"lastEditedDate\": {\n"
        + "          \"date\": {\n"
        + "            \"year\": 2019,\n"
        + "            \"month\": 11,\n"
        + "            \"day\": 6\n"
        + "          },\n"
        + "          \"time\": {\n"
        + "            \"hour\": 16,\n"
        + "            \"minute\": 39,\n"
        + "            \"second\": 45,\n"
        + "            \"nano\": 586187700\n"
        + "          }\n"
        + "        },\n"
        + "        \"createdDate\": {\n"
        + "          \"date\": {\n"
        + "            \"year\": 2019,\n"
        + "            \"month\": 11,\n"
        + "            \"day\": 6\n"
        + "          },\n"
        + "          \"time\": {\n"
        + "            \"hour\": 16,\n"
        + "            \"minute\": 39,\n"
        + "            \"second\": 45,\n"
        + "            \"nano\": 586187700\n"
        + "          }\n"
        + "        }\n"
        + "      }\n"
        + "    ],\n"
        + "    \"name\": \"New category\"\n"
        + "  }\n"
        + "]\n";
    List<Category> categoryList = noteDeserializer
        .deserializeCategoriesFromString(categoriesAsString);
    Assert.assertEquals(2, categoryList.size());
    Assert.assertEquals("qweqwe", categoryList.get(0).getName());
    Assert.assertEquals(2, categoryList.get(0).getNumNotes());
  }

}
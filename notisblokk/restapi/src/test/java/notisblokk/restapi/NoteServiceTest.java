package notisblokk.restapi;

import java.io.IOException;
import notisblokk.core.Category;
import notisblokk.core.Note;
import notiskblokk.restapi.NoteService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NoteServiceTest {

  private static NoteService noteService = new NoteService();
  private static Category backupCategory = new Category();

  @BeforeClass
  public static void beforeInit() {

  }

  @Before
  public void init() {
  }

  @Test
  public void testGetAllNotes() {
    Category category = noteService.getAllNotes();
    Assert.assertEquals(category, noteService.getAllNotes());
  }

  @Test
  public void testReplaceNote() {
    Note replacementNote = new Note("Replacement note", "Replacement note");
    noteService.addNote(new Note("Some title", "Some title"));
    int length = noteService.getAllNotes().getNumNotes();
    noteService.replaceNote(length - 1, replacementNote);
    Assert.assertEquals(replacementNote, noteService.getNote(length - 1));
    // Remove note after test
    noteService.removeNote(length - 1);
  }

  @Test
  public void addNote() {
    Note note = new Note("Add note title", "Add note message");
    noteService.addNote(note);
    int length = noteService.getAllNotes().getNumNotes();
    Assert.assertEquals(note, noteService.getNote(length - 1));
    // Remove note after test
    noteService.removeNote(length - 1);
  }

  @Test
  public void getNote() {
    int length = noteService.getAllNotes().getNumNotes();
    Note note = noteService.getNote(length - 1);
    Assert.assertEquals(note, noteService.getNote(length - 1));
  }

  @After
  public void after() {
    System.out.println("Finished.");
  }

  public NoteServiceTest() throws IOException {
  }
}

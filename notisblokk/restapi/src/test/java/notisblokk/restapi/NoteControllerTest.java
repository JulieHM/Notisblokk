package notisblokk.restapi;

import notisblokk.core.Category;
import notisblokk.core.Note;
import notiskblokk.restapi.NoteController;
import notiskblokk.restapi.RestNoteNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoteControllerTest {

  private NoteController noteController = new NoteController();
  private Category testCategory;
  private int lastCategoryIndex;

  /**
   * Runs before each test.
   */
  @Before
  public void init() {
    Note note = new Note("New title 1", "New message 1");
    Note note2 = new Note("New title 2", "New message 2");
    testCategory = new Category(note, note2);
    noteController.getNoteService().addCategory(testCategory);
    lastCategoryIndex = noteController.getNoteService().getNumCategories() - 1;
  }

  /**
   * Runs after each test.
   */
  @After
  public void finish() {
    noteController.getNoteService().removeCategory(lastCategoryIndex);
  }

  /**
   * Tests fetching of notes.
   */
  @Test
  public void testGetNotes() {
    Assert.assertEquals(testCategory.getNotes(),
        noteController.getNoteService().getAllNotes(lastCategoryIndex));
  }

  /**
   * Tests adding notes.
   */
  @Test
  public void testAddNote() {
    Note note = new Note("Add new title", "Add new message");
    noteController.getNoteService().addNote(lastCategoryIndex, note);
    int length = noteController.getNoteService().getAllNotes(lastCategoryIndex).size();
    Assert
        .assertEquals(note, noteController.getNoteService().getNote(lastCategoryIndex, length - 1));
  }

  /**
   * Tests fetching of a single note.
   */
  @Test
  public void testGetNote() {
    int length = noteController.getNoteService().getAllNotes(lastCategoryIndex).size();
    Assert.assertEquals(testCategory.getNote(length - 1),
        noteController.getNoteService().getNote(lastCategoryIndex, length - 1));
  }

  /**
   * Tests if the correct exception is thrown.
   */
  @Test(expected = RestNoteNotFoundException.class)
  public void testWrongIndexGetNote() {
    int wrongIndex = noteController.getNoteService().getAllNotes(lastCategoryIndex).size() + 5;
    noteController.getNoteService().getNote(lastCategoryIndex, wrongIndex);
  }
}

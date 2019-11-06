package notisblokk.restapi;

import java.util.List;
import notisblokk.core.Category;
import notisblokk.core.Note;
import notiskblokk.restapi.NoteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoteServiceTest {

  private static NoteService noteService = new NoteService();
  private Category testCategory;

  /**
   * Runs before each test.
   */
  @Before
  public void init() {
    testCategory = new Category("Test category");
  }

  /**
   * Tests the addCategory function.
   */
  @Test
  public void testAddCategory() {
    noteService.addCategory(testCategory);
    Assert.assertEquals(testCategory,
        noteService.getCategory(noteService.getNumCategories() - 1));
    noteService.removeCategory(noteService.getNumCategories() - 1);
  }

  /**
   * Tests the getAllCategories function.
   */
  @Test
  public void testGetAllCategories() {
    List<Category> categories = noteService.getAllCategories();
    for (int i = 0; i < categories.size(); i++) {
      Assert.assertEquals(noteService.getCategory(i), categories.get(i));
    }
  }

  /**
   * Test the getNotes function and the addNote function.
   */
  @Test
  public void testGetAllNotesAndAddNote() {
    Note note1 = new Note("Test note 1", "Message");
    Note note2 = new Note("Test note 2", "Message");
    Category category = new Category(note1, note2);
    noteService.addCategory(category);

    List<Note> notes = noteService.getCategory(noteService.getNumCategories() - 1)
        .getNotes();
    Assert.assertEquals(category.getNotes(), notes);

    noteService.removeCategory(noteService.getNumCategories() - 1);
  }

  /**
   * Tests the replaceNote function.
   */
  @Test
  public void testReplaceNote() {
    noteService.addCategory(testCategory);
    int lastCategoryIndex = noteService.getNumCategories() - 1;
    Note replacementNote = new Note("Replacement note", "Replacement note");
    noteService.addNote(lastCategoryIndex, new Note("Some title", "Some title"));
    int length = noteService.getAllNotes(noteService.getNumCategories() - 1).size();
    noteService.replaceNote(lastCategoryIndex, length - 1, replacementNote);
    Assert.assertEquals(replacementNote, noteService.getNote(lastCategoryIndex, length - 1));
    // Remove note after test
    noteService.removeCategory(lastCategoryIndex);
  }

  /**
   * Tests the getNote function.
   */
  @Test
  public void getNote() {
    testCategory.addNote(new Note("Test note", "Test message for test note"));
    noteService.addCategory(testCategory);
    int categoryIndex = noteService.getNumCategories() - 1;
    int length = noteService.getCategory(categoryIndex).getNumNotes();
    Note note = noteService.getNote(categoryIndex, length - 1);
    Assert.assertEquals(note, noteService.getNote(categoryIndex, length - 1));
    noteService.removeCategory(categoryIndex);
  }

}

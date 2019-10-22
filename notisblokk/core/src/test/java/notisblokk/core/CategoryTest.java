package notisblokk.core;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

  private Category category;

  /**
   * Creates test objects before each test is run.
   */
  @Before
  public void init() {
    category = new Category();
  }

  @Test
  public void testEmptyConstructor() {
    Assert.assertEquals(0, category.getNumNotes());
  }

  @Test
  public void testAddNote() {
    category.addNote(new Note("title", "msg"));
    Assert.assertEquals(1, category.getNumNotes());
    category.addNote(new Note("other title", "other msg"));
    Assert.assertEquals(2, category.getNumNotes());
  }

  @Test
  public void testAddNotesByCollection() {
    List<Note> noteList = new ArrayList<>();
    noteList.add(new Note("rng title", "rng msg"));
    noteList.add(new Note("test title", "test msg"));
    category.addNotes(noteList);
    Assert.assertEquals(2, category.getNumNotes());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetNoteWithInvalidIndex() {
    category.getNote(5);
  }

  @Test
  public void testGetNote() {
    Note note1 = new Note("first title", "first msg");
    Note note2 = new Note("second title", "second msg");
    Note note3 = new Note("third title", "third msg");
    category.addNotes(note1, note2, note3);
    Assert.assertEquals(note2, category.getNote(1));
  }

  @Test
  public void testGetNotes() {
    Note note1 = new Note("first title", "first msg");
    Note note2 = new Note("second title", "second msg");
    Note note3 = new Note("third title", "third msg");
    category.addNotes(note1, note2, note3);
    Assert.assertEquals(3, category.getNumNotes());
  }

  @Test
  public void testRemoveNoteByObject() {
    Note note1 = new Note("first title", "first msg");
    Note note2 = new Note("second title", "second msg");
    Note note3 = new Note("third title", "third msg");
    category.addNotes(note1, note2, note3);
    category.removeNote(note2);
    Assert.assertEquals(2, category.getNumNotes());
    Assert.assertEquals(note3, category.getNote(1));
  }

  @Test
  public void testRemoveNoteByIndex() {
    Note note1 = new Note("first title", "first msg");
    Note note2 = new Note("second title", "second msg");
    Note note3 = new Note("third title", "third msg");
    category.addNotes(note1, note2, note3);
    category.removeNote(0);
    Assert.assertEquals(2, category.getNumNotes());
    Assert.assertEquals(note3, category.getNote(1));
  }

  @Test
  public void testReplaceNote() {
    Note noteToBeReplaced = new Note("Replacement note", "Replacement note");
    int replacementIndex = 7;
    for (var i = 0; i < 10; i++) {
      category.addNote(new Note("Some title", "Some message"));
    }
    category.replaceNote(replacementIndex, noteToBeReplaced);
    Assert.assertEquals(category.getNote(replacementIndex), noteToBeReplaced);
  }

}

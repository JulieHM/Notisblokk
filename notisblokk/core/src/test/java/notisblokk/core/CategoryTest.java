package notisblokk.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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

  /**
   * Tests the empty constructor.
   */
  @Test
  public void testEmptyConstructor() {
    Assert.assertEquals(0, category.getNumNotes());
  }

  /**
   * Tests the addNote function.
   */
  @Test
  public void testAddNote() {
    category.addNote(new Note("title", "msg"));
    Assert.assertEquals(1, category.getNumNotes());
    category.addNote(new Note("other title", "other msg"));
    Assert.assertEquals(2, category.getNumNotes());
  }

  @Test
  public void testStringListConstructor() {
    String name = "Test name";
    Note note1 = new Note("Note 1", "Note 1");
    Note note2 = new Note("Note 2", "Note 2");
    List<Note> noteList = new ArrayList<>();
    noteList.add(note1);
    noteList.add(note2);
    Category category = new Category(name, noteList);
    Assert.assertEquals(name, category.getName());
    Assert.assertEquals(noteList, category.getNotes());
  }

  @Test
  public void testNotesConstructor() {
    Note note1 = new Note("Note 1", "Note 1");
    Note note2 = new Note("Note 2", "Note 2");
    Category category = new Category(note1, note2);
    Assert.assertEquals(note1, category.getNote(0));
    Assert.assertEquals(note2, category.getNote(1));
  }

  @Test
  public void testNoteCollectionConstructor() {
    Note note1 = new Note("Note 1", "Note 1");
    Note note2 = new Note("Note 2", "Note 2");
    Collection<Note> noteCollection = new ArrayList<>();
    noteCollection.add(note1);
    noteCollection.add(note2);
    Category category = new Category(noteCollection);
    Assert.assertEquals(note1, category.getNote(0));
    Assert.assertEquals(note2, category.getNote(1));
  }

  @Test
  public void testIterator() {
    List<Note> notes = new ArrayList<>();
    Note note1 = new Note("Note 1", "Note 1");
    Note note2 = new Note("Note 2", "Note 2");
    notes.add(note1);
    notes.add(note2);
    Category category = new Category(notes);
    Assert.assertEquals(notes.iterator().getClass(), category.iterator().getClass());
  }

  /**
   * Tests the addNotes function.
   */
  @Test
  public void testAddNotesByCollection() {
    List<Note> noteList = new ArrayList<>();
    noteList.add(new Note("rng title", "rng msg"));
    noteList.add(new Note("test title", "test msg"));
    category.addNotes(noteList);
    Assert.assertEquals(2, category.getNumNotes());
  }

  /**
   * Tests if an exception is thrown if given the wrong index.
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetNoteWithInvalidIndex() {
    category.getNote(5);
  }

  /**
   * Tests the fetching of a specific note.
   */
  @Test
  public void testGetNote() {
    Note note1 = new Note("first title", "first msg");
    Note note2 = new Note("second title", "second msg");
    Note note3 = new Note("third title", "third msg");
    category.addNotes(note1, note2, note3);
    Assert.assertEquals(note2, category.getNote(1));
  }

  /**
   * Tests the fetching of all notes.
   */
  @Test
  public void testGetNotes() {
    Note note1 = new Note("first title", "first msg");
    Note note2 = new Note("second title", "second msg");
    Note note3 = new Note("third title", "third msg");
    category.addNotes(note1, note2, note3);
    Assert.assertEquals(3, category.getNumNotes());
  }

  /**
   * Tests removing a note by passing in the object.
   */
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

  /**
   * Tests removing a note by index in the array.
   */
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

  /**
   * Tests the replaceNote function.
   */
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

  @Test
  public void testSetName() {
    Category category = new Category("No name");
    String newName = "TEST NAME";
    category.setName(newName);
    Assert.assertEquals(newName, category.getName());
  }

  @Test
  public void testToString() {
    Note note1 = new Note("first title", "first msg");
    Note note2 = new Note("second title", "second msg");
    Note note3 = new Note("third title", "third msg");
    Category category = new Category(note1, note2, note3);
    String actualToString = "Notes{"
        + "size=" + category.getNumNotes()
        + ", notes=" + category.getNotes()
        + '}';

    Assert.assertEquals(actualToString, category.toString());
  }

}

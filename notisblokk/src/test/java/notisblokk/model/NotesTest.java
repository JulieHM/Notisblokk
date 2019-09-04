package notisblokk.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NotesTest {

  private Notes notes;

  @Before
  public void init() {
    notes = new Notes();
  }

  @Test
  public void testEmptyConstructor() {
    Assert.assertEquals(0, notes.getNumNotes());
  }

  @Test
  public void testAddNote() {
    notes.addNote(new Note("title", "msg"));
    Assert.assertEquals(1, notes.getNumNotes());
    notes.addNote(new Note("other title", "other msg"));
    Assert.assertEquals(2, notes.getNumNotes());
  }

  @Test
  public void testAddNotesByCollection() {
    List<Note> noteList = new ArrayList<>();
    noteList.add(new Note("rng title", "rng msg"));
    noteList.add(new Note("test title", "test msg"));
    notes.addNotes(noteList);
    Assert.assertEquals(2, notes.getNumNotes());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetNoteWithInvalidIndex() {
    notes.getNote(5);
  }

  @Test
  public void testGetNote() {
    Note note1 = new Note("first title", "first msg");
    Note note2 = new Note("second title", "second msg");
    Note note3 = new Note("third title", "third msg");
    notes.addNotes(note1, note2, note3);
    Assert.assertEquals(note2, notes.getNote(1));
  }

  @Test
  public void testGetNotes() {
    Note note1 = new Note("first title", "first msg");
    Note note2 = new Note("second title", "second msg");
    Note note3 = new Note("third title", "third msg");
    notes.addNotes(note1, note2, note3);
    Assert.assertEquals(3, notes.getNumNotes());
  }
  }

}

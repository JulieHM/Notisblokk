package notisblokk.model;

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
}

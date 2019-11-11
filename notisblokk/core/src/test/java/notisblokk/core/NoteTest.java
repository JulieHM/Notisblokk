package notisblokk.core;

import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoteTest {

  private Note note;
  private LocalDateTime time;

  /**
   * Creates test objects before each test is run.
   */
  @Before
  public void init() {
    time = LocalDateTime.of(2050, 2, 20, 4, 50, 43);
    note = new Note("title", "msg", time, time);
  }

  @Test
  public void testToString() {
    String correctToString = "Note{title='title', message='msg', "
        + "lastEditedDate=" + time + ", createdDate=" + time + "}";
    Assert.assertEquals(correctToString, note.toString());
  }

  @Test
  public void testSetLastEditedDate() {
    note.setLastEditedDate();
    Assert.assertNotEquals(time, note.getLastEditedDate());
    Assert.assertEquals(time, note.getCreatedDate());
  }

  @Test
  public void testSetMessageAndSetTitle() {
    Note note = new Note("To be tested", "To be tested");
    String title = "New note title";
    String message = "New note message";
    note.setTitle(title);
    note.setMessage(message);
    Assert.assertEquals(title, note.getTitle());
    Assert.assertEquals(message, note.getMessage());
  }

  @Test
  public void testEquals() throws InterruptedException {
    Note noteToBeCompared = new Note("Comparison note", "Comparison note");
    int wrongObject = 5;
    Assert.assertNotEquals(noteToBeCompared, wrongObject);
    Assert.assertNotEquals(noteToBeCompared, null);
    Thread.sleep(1);
    // To hit all branches, create four wrong notes, each having one attribute wrong.
    Note wrongNote1 = new Note(noteToBeCompared.getTitle(), noteToBeCompared.getMessage(),
        noteToBeCompared.getCreatedDate(), LocalDateTime.now());
    Note wrongNote2 = new Note(noteToBeCompared.getTitle(), noteToBeCompared.getMessage(),
        LocalDateTime.now(), noteToBeCompared.getLastEditedDate());
    Note wrongNote3 = new Note(noteToBeCompared.getTitle(), "noteToBeCompared.getMessage()",
        noteToBeCompared.getCreatedDate(), noteToBeCompared.getLastEditedDate());
    Note wrongNote4 = new Note("noteToBeCompared.getTitle()", noteToBeCompared.getMessage(),
        noteToBeCompared.getCreatedDate(), noteToBeCompared.getLastEditedDate());
    Note correctNote = new Note(noteToBeCompared.getTitle(), noteToBeCompared.getMessage(),
        noteToBeCompared.getCreatedDate(), noteToBeCompared.getLastEditedDate());
    Assert.assertNotEquals(noteToBeCompared, wrongNote1);
    Assert.assertNotEquals(noteToBeCompared, wrongNote2);
    Assert.assertNotEquals(noteToBeCompared, wrongNote3);
    Assert.assertNotEquals(noteToBeCompared, wrongNote4);
    Assert.assertEquals(noteToBeCompared, correctNote);
  }
}

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
    /*
    String correctToString = "Note{title='title', message='msg', "
        + "lastEditedDate=" + time + ", createdDate=" + time + "}";
     */
    String correctToString = "title";
    Assert.assertEquals(correctToString, note.toString());
  }

  @Test
  public void testSetLastEditedDate() {
    note.setLastEditedDate();
    Assert.assertNotEquals(time, note.getLastEditedDate());
    Assert.assertEquals(time, note.getCreatedDate());
  }
}

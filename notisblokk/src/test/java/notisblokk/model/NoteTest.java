package notisblokk.model;

import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

public class NoteTest {

  @Test
  public void testToString() {
    final LocalDateTime time = LocalDateTime.now();
    final Note note = new Note("title", "msg", time, time);
    String correctToString = "Note{title='title', message='msg', "
        + "lastEditedDate=" + time + ", createdDate=" + time + "}";
    Assert.assertEquals(correctToString, note.toString());
  }
}

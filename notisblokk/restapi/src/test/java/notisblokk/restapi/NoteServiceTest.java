package notisblokk.restapi;

import java.util.List;
import java.io.IOException;
import notisblokk.core.Note;
import notisblokk.json.NoteDeserializer;
import notiskblokk.restapi.NoteService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoteServiceTest {

  public NoteService noteService = new NoteService();
  private Note replacementNote = new Note("Replacement note", "Replacement note");

  @Before
  public void init() {
    System.out.println("Start test");
  }

  @Test
  public void noteServiceTest() {
    noteService.addNote(new Note("Some title", "Some title"));
    int length = noteService.getAllNotes().getNumNotes();
    noteService.replaceNote(length-1, replacementNote);
    Assert.assertEquals(replacementNote, noteService.getNote(length-1));
  }

  @After
  public void after(){
    System.out.println("End test");
  }

  public NoteServiceTest() throws IOException {
  }
}

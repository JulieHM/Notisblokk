package notisblokk.restapi;

import notisblokk.core.Note;
import notiskblokk.restapi.NoteController;
import notiskblokk.restapi.NoteService;
import notiskblokk.restapi.RestNoteNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class NoteControllerTest {

  private NoteController noteController = new NoteController();
  private NoteService noteService = new NoteService();

  @Before
  public void init() {

  }

  @Test
  public void testGetNotes() {
    Assert.assertEquals(noteService.getAllNotes(), noteController.getNoteService().getAllNotes());
  }

  @Test
  public void testAddNote() {
    Note note = new Note("New title", "New message");
    noteController.getNoteService().addNote(note);
    int length = noteController.getNoteService().getAllNotes().getNumNotes();
    Assert.assertEquals(note, noteController.getNoteService().getNote(length - 1));
    // Delete note after test
    noteController.getNoteService().removeNote(length - 1);
  }

  @Test
  public void testGetNote() {
    int length = noteController.getNotes().getNumNotes();
    Assert.assertEquals(noteService.getNote(length - 1),
        noteController.getNoteService().getNote(length - 1));
  }

  @Test(expected = RestNoteNotFoundException.class)
  public void testWrongIndexGetNote() {
    int wrongIndex = noteController.getNoteService().getAllNotes().getNumNotes() + 5;
    noteController.getNote(wrongIndex);
  }

}

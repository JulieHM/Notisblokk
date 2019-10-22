package notisblokk.restapi;

import notisblokk.core.Note;
import notiskblokk.restapi.NoteController;
import notiskblokk.restapi.NoteService;
import notiskblokk.restapi.RestNoteNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoteControllerTest {

  private NoteController noteController = new NoteController();
  private NoteService noteService = new NoteService();

  /**
   * Runs before each test
   */
  @Before
  public void init() {
    Note note = new Note("New title", "New message");
    noteController.getNoteService().addNote(note);
  }

  /**
   * Runs after each test
   */
  @After
  public void finish() {
    int length = noteController.getNoteService().getAllNotes().getNumNotes();
    for (int i = 0; i < length - 1; i++) {
      noteController.getNoteService().removeNote(i);
    }
  }

  @Test
  public void testGetNotes() {
    Assert.assertEquals(noteService.getAllNotes(), noteController.getNoteService().getAllNotes());
  }

  @Test
  public void testAddNote() {
    Note note = new Note("Add new title", "Add new message");
    noteController.getNoteService().addNote(note);
    int length = noteController.getNoteService().getAllNotes().getNumNotes();
    Assert.assertEquals(note, noteController.getNoteService().getNote(length - 1));
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

package notiskblokk.restapi;

import java.io.IOException;
import java.util.Optional;
import jdk.jfr.events.ExceptionThrownEvent;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import notisblokk.json.NoteDeserializer;
import notisblokk.json.NoteSerializer;
import org.springframework.stereotype.Repository;

@Repository
public class NoteService {

  private static final String SAVE_PATH = System.getProperty("user.home")
      + "/.projectNotes/notes.json"; // TODO: move path away from FxAppController class

  private static Notes notes = new Notes();

  static {
    loadNotesFromJson();
  }

  private static void loadNotesFromJson() {
    NoteDeserializer noteDeserializer = new NoteDeserializer();
    try {
      notes.addNotes(noteDeserializer.deserializeNotes(SAVE_PATH));
    } catch (IOException e) {
      System.err.println("Unable to deserialize notes from json.");
    }
  }

  private void saveNotesToJson() {
    NoteSerializer noteSerializer = new NoteSerializer();
    try {
      noteSerializer.serializeNotes(notes.getNotes(), SAVE_PATH);
    } catch (IOException e) {
      System.err.println("Unable to save notes to json.");
    }
  }

  public Notes getAllNotes() {
    return notes;
  }

  public boolean addNote(Note note) {
    if (notes.addNote(note)) {
      saveNotesToJson();
      return true;
    }
    throw new RestInvalidNoteFormatException(note);
  }

  public Note getNote(int index) {
    try {
      return notes.getNote(index);
    } catch (IndexOutOfBoundsException e) {
      throw new RestNoteNotFoundException(index);
    }
  }

  public Note replaceNote(int index, Note note) {
    notes.replaceNote(index, note);
    saveNotesToJson();
    return note;
  }

  boolean removeNote(int index) {
    try {
      notes.removeNote(index);
      saveNotesToJson();
      return true;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }
}


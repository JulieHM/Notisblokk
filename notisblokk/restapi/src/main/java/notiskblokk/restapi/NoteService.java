package notiskblokk.restapi;

import java.io.IOException;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import notisblokk.json.NoteDeserializer;
import notisblokk.json.NoteSerializer;
import org.springframework.stereotype.Repository;

@Repository
public class NoteService {

  /**
   * Save path used for local saving.
   */
  private static final String SAVE_PATH = System.getProperty("user.home")
      + "/.projectNotes/notes.json";

  private static Notes notes = new Notes();

  static {
    loadNotesFromJson();
  }

  /**
   * Fetches all the notes stored in the local notes.json-file.
   */
  private static void loadNotesFromJson() {
    NoteDeserializer noteDeserializer = new NoteDeserializer();
    try {
      notes.addNotes(noteDeserializer.deserializeLocalNotes(SAVE_PATH));
    } catch (IOException e) {
      System.err.println("Unable to deserialize notes from json.");
    }
  }

  /**
   * Saves all the notes to the local notes.json-file.
   */
  private void saveNotesToJson() {
    NoteSerializer noteSerializer = new NoteSerializer();
    try {
      noteSerializer.serializeNotesToLocal(notes.getNotes(), SAVE_PATH);
    } catch (IOException e) {
      System.err.println("Unable to save notes to json.");
    }
  }

  /**
   * Returns all notes
   *
   * @return
   */
  public Notes getAllNotes() {
    return notes;
  }

  /**
   * Adds a note to the iterable Notes and saves them. Returns true if successful, false if not.
   *
   * @param note
   * @return
   */
  public boolean addNote(Note note) {
    if (notes.addNote(note)) {
      saveNotesToJson();
      return true;
    }
    return false;
  }

  /**
   * Returns the note at the given index. Throws RestNoteNotFoundException if not found.
   *
   * @param index
   * @return
   */
  public Note getNote(int index) {
    try {
      return notes.getNote(index);
    } catch (IndexOutOfBoundsException e) {
      throw new RestNoteNotFoundException(index);
    }
  }

  /**
   * Replaces the note at index "index" with the note passed in.
   *
   * @param index
   * @param note
   * @return
   */
  public Note replaceNote(int index, Note note) {
    notes.replaceNote(index, note);
    saveNotesToJson();
    return note;
  }

  /**
   * Removes the note at a given index and save the new iterable Notes to local. Returns true if
   * successful, false if the index is out of bounds.
   *
   * @param index
   * @return
   */
  public boolean removeNote(int index) {
    try {
      notes.removeNote(index);
      saveNotesToJson();
      return true;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }
}


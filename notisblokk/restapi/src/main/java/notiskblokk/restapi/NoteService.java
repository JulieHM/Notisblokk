package notiskblokk.restapi;

import java.io.IOException;
import notisblokk.core.Category;
import notisblokk.core.Note;
import notisblokk.json.NoteDeserializer;
import notisblokk.json.NoteSerializer;
import org.springframework.stereotype.Repository;

@Repository
public class NoteService {

  /**
   * Save path used for local saving.
   */
  private static final String SAVE_PATH = System.getProperty("user.home")
      + "/it1901/notisblokk/notes.json";

  private static Category category = new Category();

  static {
    loadNotesFromJson();
  }

  /**
   * Fetches all the notes stored in the local notes.json-file.
   */
  private static void loadNotesFromJson() {
    NoteDeserializer noteDeserializer = new NoteDeserializer();
    try {
      category.addNotes(noteDeserializer.deserializeLocalNotes(SAVE_PATH));
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
      noteSerializer.serializeNotesToLocal(category.getNotes(), SAVE_PATH);
    } catch (IOException e) {
      System.err.println("Unable to save notes to json.");
    }
  }

  /**
   * Returns all notes
   */
  public Category getAllNotes() {
    return category;
  }

  /**
   * Adds a note to the iterable Notes and saves them. Returns true if successful, false if not.
   */
  public boolean addNote(Note note) {
    if (category.addNote(note)) {
      saveNotesToJson();
      return true;
    }
    return false;
  }

  /**
   * Returns the note at the given index. Throws RestNoteNotFoundException if not found.
   */
  public Note getNote(int index) {
    try {
      return category.getNote(index);
    } catch (IndexOutOfBoundsException e) {
      throw new RestNoteNotFoundException(index);
    }
  }

  /**
   * Replaces the note at index "index" with the note passed in.
   */
  public Note replaceNote(int index, Note note) {
    category.replaceNote(index, note);
    saveNotesToJson();
    return note;
  }

  /**
   * Removes the note at a given index and save the new iterable Notes to local. Returns true if
   * successful, false if the index is out of bounds.
   */
  public boolean removeNote(int index) {
    try {
      category.removeNote(index);
      saveNotesToJson();
      return true;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }
}


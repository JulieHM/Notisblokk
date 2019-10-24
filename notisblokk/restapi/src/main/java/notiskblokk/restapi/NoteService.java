package notiskblokk.restapi;

import java.io.IOException;
import java.util.List;
import notisblokk.core.Category;
import notisblokk.core.Note;
import notisblokk.core.Notebook;
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

  private static Notebook notebook = new Notebook();

  static {
    loadNotesFromJson();
  }

  /**
   * Fetches all the notes stored in the local notes.json-file.
   */
  private static void loadNotesFromJson() {
    NoteDeserializer noteDeserializer = new NoteDeserializer();
    /*
    try {
      notebook.getActiveCategory().addNotes(noteDeserializer.deserializeLocalNotes(SAVE_PATH));
    } catch (IOException e) {
      System.err.println("Unable to deserialize notes from json.");
    }
     */
  }

  /**
   * Saves all the notes to the local notes.json-file.
   */
  private void saveNotesToJson() {
    NoteSerializer noteSerializer = new NoteSerializer();
    /*
    try {
      noteSerializer.serializeNotesToLocal(notebook.getActiveCategory().getNotes(), SAVE_PATH);
    } catch (IOException e) {
      System.err.println("Unable to save notes to json.");
    }
     */
  }

  /**
   * Saves the notebook to the local notes.json-file.
   */
  private void saveNotebookToJson() {
    NoteSerializer noteSerializer = new NoteSerializer();
    try {
      noteSerializer.serializeNotebookToLocal(notebook.getCategories(), SAVE_PATH);
    } catch (IOException e) {
      System.err.println("Unable to save notes to json.");
    }
  }

  /**
   * Returns all notes
   */
  public List<Note> getAllNotes() {
    return notebook.getActiveCategory().getNotes();
  }

  /**
   * Returns all categories
   */
  public List<Category> getAllCategories() {
    return notebook.getCategories();
  }

  /**
   * Gets the number of categories in the notebook
   * @return
   */
  public int getNumCategories(){
    return notebook.getCategories().size();
  }

  /**
   * Adds a note to the iterable Notes and saves them. Returns true if successful, false if not.
   */
  public boolean addNote(Note note) {
    if (notebook.getActiveCategory().addNote(note)) {
      saveNotesToJson();
      return true;
    }
    return false;
  }

  /**
   * Adds a notebook.getActiveCategory() to the notebook
   *
   * @param category
   * @return
   */
  public boolean addCategory(Category category) {
    if (notebook.addCategory(category)) {
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
      return notebook.getActiveCategory().getNote(index);
    } catch (IndexOutOfBoundsException e) {
      throw new RestNoteNotFoundException(index);
    }
  }

  /**
   * Gets the category at the fi
   * @param index
   * @return
   */
  public Category getCategory(int index) {
    try {
      return notebook.getActiveCategory();
    } catch (IndexOutOfBoundsException e) {
      throw new RestNoteNotFoundException(index);
    }
  }

  /**
   * Replaces the note at index "index" with the note passed in.
   */
  public Note replaceNote(int index, Note note) {
    notebook.getActiveCategory().replaceNote(index, note);
    saveNotesToJson();
    return note;
  }

  /**
   * Renames the active notebook.getActiveCategory()
   *
   * @param name
   * @return
   */
  public Category renameCategory(String name) {
    notebook.getActiveCategory().setName(name);
    saveNotesToJson();
    return notebook.getActiveCategory();
  }

  /**
   * Removes the note at a given index and save the new iterable Notes to local. Returns true if
   * successful, false if the index is out of bounds.
   */
  public boolean removeNote(int index) {
    try {
      notebook.getActiveCategory().removeNote(index);
      saveNotesToJson();
      return true;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Deletes a notebook.getActiveCategory() =)
   * @param index
   * @return
   */
  public boolean removeCategory(int index) {
    try {
      notebook.removeCategory(index);
      saveNotesToJson();
      return true;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }
}


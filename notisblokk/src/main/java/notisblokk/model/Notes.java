package notisblokk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Notes implements Iterable<Note> {

  private List<Note> notes = new ArrayList<>();

  /**
   * The default constructor for creating a Notes object. This
   * will have an empty List of Note's.
   */
  public Notes() {
    // Possible to create an empty object of Notes
  }

  /**
   * Creates a new Notes object with a List of the given Note(s).
   * @param note The Note or array of Notes to add to the List of Notes.
   */
  public Notes(Note... note) {
    addNotes(note);
  }

  /**
   * Creates a new Note object with a List of the given Note(s).
   * @param notes The collection of Note's to add to the List of Notes.
   */
  public Notes(Collection<Note> notes) {
    addNotes(notes);
  }

  @Override
  public Iterator<Note> iterator() {
    return notes.iterator();
  }

  /**
   * Adds a single new note to the List of Notes.
   * @param note The Note to add to the List.
   */
  public void addNote(Note note) {
    // NOTE: possibly return the index it is placed in?
    notes.add(note);
  }

  /**
   * Adds a new Note or array of Note's to the List of Notes.
   * @param note The Note(s) to add to the List.
   */
  public void addNotes(Note... note) {
    addNotes(Arrays.asList(note));
  }

  /**
   * Adds all the notes from the collection to to the List of Notes.
   * @param notes The Notes to add to the List.
   */
  public void addNotes(Collection<Note> notes) {
    // NOTE: possibly return the index it is placed in?
    this.notes.addAll(notes);
  }

  /**
   * Removes a given Note from the List of Notes.
   * @param note The Note to remove.
   */
  public void removeNote(Note note) {
    notes.remove(note);
  }

  /**
   * Removes a given Note from the List of Notes.
   * @param index The index of the Note to remove.
   */
  public void removeNote(int index) {
    notes.remove(index);
  }

  /**
   * Gets the Note located at a given index.
   * @param index The index of the Note to return.
   * @return The Note located at the given index.
   */
  public Note getNote(int index) {
    return notes.get(index);
  }

  /**
   * Gets all the Notes in the List of Notes.
   * @return The List of Notes.
   */
  public List<Note> getNotes() {
    return notes;
  }

  /**
   * Gets the size of the List of Notes.
   * @return The size of the List.
   */
  public int getNumNotes() {
    return notes.size();
  }

  @Override
  public String toString() {
    return "Notes{"
        + "size=" + getNumNotes()
        + ", notes=" + notes
        + '}';
  }
}

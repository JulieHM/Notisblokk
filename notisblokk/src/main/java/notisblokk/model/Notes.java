package notisblokk.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Notes implements Iterable<Note> {

  private List<Note> notes = new ArrayList<>();

  public Notes() {
    // Possible to create an empty object of Notes
  }

  public Notes(Note note) {
    addNote(note);
  }

  public Notes(Collection<Note> notes) {
    addNotes(notes);
  }

  @Override
  public Iterator<Note> iterator() {
    return notes.iterator();
  }

  public void addNote(Note note) {
    // NOTE: possibly return the index it is placed in?
    notes.add(note);
  }

  public void addNotes(Collection<Note> notes) {
    // NOTE: possibly return the index it is placed in?
    this.notes.addAll(notes);
  }

  public void removeNote(Note note) {
    notes.remove(note);
  }

  public void removeNote(int index) {
    notes.remove(index);
  }

  public Note getNote(int index) {
    return notes.get(index);
  }

  public List<Note> getNotes() {
    return notes;
  }

  public int getNumNotes() {
    return notes.size();
  }

  @Override
  public String toString() {
    return "Notes{" +
        "size=" + getNumNotes() +
        ", notes=" + notes +
        '}';
  }
}

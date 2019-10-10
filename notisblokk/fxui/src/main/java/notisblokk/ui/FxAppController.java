package notisblokk.ui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import notisblokk.json.NoteDeserializer;
import notisblokk.json.NoteSerializer;

public class FxAppController {

  @FXML
  private TextField titleField;

  @FXML
  private TextArea messageField;

  @FXML
  private ListView<Note> noteListView;

  private NotesDataClass notesDataClass = new NotesDataClass();
  /**
   * Initializes the graphical user interface. Loads previous notes and displays the most recent.
   */
  @FXML
  public void initialize() {
    /* Load and display notes in ListView */
    // savedNotes.sortNotesByLastEdited();
    noteListView.getItems().addAll(notesDataClass.getNotes()); // DataClass.getallNotes()
    noteListView.setCellFactory(listView -> new NoteCell()); // custom cell display

    /* Select and display the most recently edited note */
    if (savedNotes.getNumNotes() > 0) { // TODO: getNotes -> add to list -> get length of list
      noteListView.getSelectionModel().select(0); // sorted list means index 0 is last edited
      displayNote(noteListView.getSelectionModel().getSelectedItem());
    }
  }

  public void setSavedNotes(final Notes savedNotes) {
    this.savedNotes = savedNotes;
    noteListView.getItems().clear();
    updateLocationViewList(0);
  }

  private void updateLocationViewList(int selectedIndex) {
    final Note[] noteArray = new Note[savedNotes.getNumNotes()];
    for (int i = 0; i < noteArray.length; i++) {
      noteArray[i] = savedNotes.getNote(i);
    }
    final int oldSelectionIndex = noteListView.getSelectionModel().getSelectedIndex();
    noteListView.setItems(FXCollections.observableArrayList(noteArray));
    if (selectedIndex < 0 || selectedIndex >= noteArray.length) {
      selectedIndex = oldSelectionIndex;
    }
    if (selectedIndex >= 0 && selectedIndex < savedNotes.getNumNotes()) {
      noteListView.getSelectionModel().select(selectedIndex);
    }
    displayNote(noteListView.getSelectionModel().getSelectedItem());
  }

  /**
   * Event Handler for the ListView. Displays the contents of the selected note.
   */
  @FXML
  public void onSelectNoteClick() {
    displayNote(noteListView.getSelectionModel().getSelectedItem()); // display selected note
  }

  /**
   * Event Handler for the New Note button. Creates a new empty note and displays it, ready to be
   * edited.
   */
  @FXML
  public void onNewNoteClick() {
    /* Create new note and add to lists */
    Note note = new Note("New note", "");
    savedNotes.addNote(note);
    noteListView.getItems().add(note);

    /* Select and display the new note */
    int index = savedNotes.getNumNotes() - 1; // new note will always be the last index
    noteListView.getSelectionModel().select(index);
    noteListView.scrollTo(index); // scrolls to the node in the ListView in case it is not visible
    displayNote(note);
  }

  /**
   * Event Handler for the Save button. Updates the currently edited note and saves [all] notes to
   * json.
   */
  @FXML
  private void onSaveClick() {
    updateNoteInfo(noteListView.getSelectionModel().getSelectedItem()); // update selected note
    noteListView.refresh(); // update ListView in case of title change
    saveNotesToJson(); // TODO: Save note to server
  }

  /**
   * Displays the given note's content in the edit view.
   *
   * @param note The note to be displayed.
   */
  private void displayNote(Note note) {
    titleField.setText(note.getTitle());
    messageField.setText(note.getMessage());
  }

  /**
   * Updates the title, message and last edited date of the given note.
   *
   * @param note The note to be updated.
   */
  void updateNoteInfo(Note note) {
    note.setLastEditedDate(); // sets it to current date/time
    note.setMessage(messageField.getText());
    note.setTitle(titleField.getText());
  }

  @FXML
  private void onDeleteClick() {
    Note selectedNote = noteListView.getSelectionModel().getSelectedItem();
    savedNotes.removeNote(selectedNote);
    noteListView.getItems().remove(selectedNote);
    displayNote(noteListView.getSelectionModel().getSelectedItem());
  }
}

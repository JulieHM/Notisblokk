package notisblokk.ui;

import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import notisblokk.core.Note;

/**
 * FxAppController is communicating with the server through REST API. All data displayed in the GUI
 * is controlled by the API.
 */
public class FxAppController {

  @FXML
  private TextField titleField;

  @FXML
  private TextArea messageField;

  @FXML
  private ListView<Note> noteListView;

  private NotesDataAccess notesDataAccess = new NotesDataAccess();

  /**
   * Initializes the graphical user interface. Set up custom cell factory and updates the List View
   * to display the initial notes loaded from the REST API.
   */
  @FXML
  public void initialize() {
    noteListView.setCellFactory(listView -> new NoteCell());
    updateNoteListView(0);
  }

  /**
   * Event Handler for the ListView.
   */
  @FXML
  public void onSelectNoteClick() {
    displaySelectedNote();
  }

  /**
   * Event Handler for the New Note button.
   */
  @FXML
  public void onNewNoteClick() {
    Note note = new Note("New note", "");
    notesDataAccess.addNote(note);
    int index = noteListView.getItems().size(); // current size will be the new index
    updateNoteListView(index);
  }

  /**
   * Event Handler for the Save button.
   */
  @FXML
  private void onSaveClick() {
    int selectedIndex = noteListView.getSelectionModel().getSelectedIndex();
    Note selectedNote = notesDataAccess.getNote(selectedIndex); // possibly redundant, use local?
    updateNoteInfo(selectedNote);
    notesDataAccess.updateNote(selectedIndex, selectedNote);
    updateNoteListView(selectedIndex);
  }

  /**
   * Event Handler for the Delete button.
   */
  @FXML
  private void onDeleteClick() {
    int selectedIndex = noteListView.getSelectionModel().getSelectedIndex();
    notesDataAccess.removeNote(selectedIndex);
    updateNoteListView(selectedIndex);
  }

  /**
   * Displays the content of the note selected in the List View
   */
  private void displaySelectedNote() {
    int selectedIndex = noteListView.getSelectionModel().getSelectedIndex();
    Note selectedNote = notesDataAccess.getNote(selectedIndex);
    titleField.setText(selectedNote.getTitle());
    messageField.setText(selectedNote.getMessage());
    noteListView.scrollTo(selectedIndex); // scroll up/down in list view if needed
  }

  /**
   * Updates the title, message and last edited date of the given note.
   *
   * @param note The note to be updated.
   */
  private void updateNoteInfo(Note note) {
    note.setLastEditedDate(); // sets it to current date/time
    note.setMessage(messageField.getText());
    note.setTitle(titleField.getText());
  }

  /**
   * Updates the content of the List View through the REST API. Selects and displays the note by the
   * given selectedIndex.
   *
   * @param selectedIndex The new index to select in the list view
   */
  private void updateNoteListView(int selectedIndex) {
    final Collection<Note> noteArray = notesDataAccess.getNotes();
    final int oldSelectionIndex = noteListView.getSelectionModel().getSelectedIndex();
    noteListView.setItems(FXCollections.observableArrayList(noteArray));
    if (selectedIndex < 0 || selectedIndex >= noteArray.size()) {
      selectedIndex = oldSelectionIndex;
    }
    if (selectedIndex >= 0 && selectedIndex < noteArray.size()) {
      noteListView.getSelectionModel().select(selectedIndex);
    }
    displaySelectedNote();
  }

  /**
   * USED TO ALLOW TESTING
   */
  public void setNotesDataAccess(final NotesDataAccess notesDataAccess) {
    this.notesDataAccess = notesDataAccess;
    noteListView.getItems().clear();
    updateNoteListView(0);
  }
}

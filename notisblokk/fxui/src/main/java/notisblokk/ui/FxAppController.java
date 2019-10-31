package notisblokk.ui;

import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import notisblokk.core.Category;
import notisblokk.core.Note;
import notisblokk.core.Notebook;

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

  @FXML
  private TabPane categoryTabPane;

  private TabSetText tabSetText = new TabSetText(this);

  private Notebook notebook = new Notebook();

  private Category activeCategory = new Category();

  private NotesDataAccess notesDataAccess = new NotesDataAccess();

  /**
   * Initializes the graphical user interface. Set up custom cell factory and updates the List View
   * to display the initial notes loaded from the REST API.
   */
  @FXML
  public void initialize() {
    initTabView();
    updateCategoryTabView();
    noteListView.setCellFactory(listView -> new NoteCell());
    activeCategory = notebook.getCategory(0);
  }


  /**
   * Add a new tab
   */
  @FXML
  private void onNewCategoryClick() {
    Note emptyNote = new Note("Empty note", "");
    Category newCategory = new Category("New category");
    newCategory.addNote(emptyNote);
    notebook.addCategory(newCategory);
    notesDataAccess.addCategory(newCategory);
    Tab categoryTab = tabSetText.createEditableTab("New category", newCategory);
    categoryTabPane.getTabs().addAll(categoryTab);
    categoryTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
    categoryTabPane.getSelectionModel().select(categoryTab);
    activeCategory = newCategory;
    updateNoteListView(notesDataAccess.getCategories().size() - 1);
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
    notesDataAccess.addNote(notebook.getGetCategoryIndex(activeCategory), note);
    int index = noteListView.getItems().size(); // current size will be the new index
    updateNoteListView(index);
  }


  /**
   * Event Handler for the Save button.
   */
  @FXML
  private void onSaveClick() {
    int selectedIndex = noteListView.getSelectionModel().getSelectedIndex();
    Note selectedNote = notesDataAccess.getNote(notebook.getGetCategoryIndex(activeCategory),
        selectedIndex);
    updateNoteInfo(selectedNote);
    notesDataAccess
        .updateNote(notebook.getGetCategoryIndex(activeCategory), selectedIndex, selectedNote);
    updateNoteListView(selectedIndex);
  }

  /**
   * Event Handler for the Delete button.
   */
  @FXML
  private void onDeleteClick() {
    int selectedIndex = noteListView.getSelectionModel().getSelectedIndex();
    notesDataAccess.removeNote(notebook.getGetCategoryIndex(activeCategory), selectedIndex);
    updateNoteListView(selectedIndex);
  }

  public void deleteCategory(Category category) {
    notesDataAccess.deleteCategory(notebook.getGetCategoryIndex(category));
    int count = notesDataAccess.getCategories().size();
    updateNoteListView(count - 1);
  }

  /**
   * Renames the given category with the new name
   *
   * @param category category to be renamed
   * @param newName  new name
   */
  public void renameCategory(Category category, String newName) {
    int index = notebook.getGetCategoryIndex(category);
    notebook.getCategory(index).setName(newName);
    notesDataAccess.renameCategory(category, index);
  }

  /**
   * Displays the content of the note selected in the List View
   */
  private void displaySelectedNote() {
    int selectedIndex = noteListView.getSelectionModel().getSelectedIndex();
    Note selectedNote = notesDataAccess
        .getNote(notebook.getGetCategoryIndex(activeCategory), selectedIndex);
    System.out.println(selectedNote);
    if (selectedNote != null) {
      titleField.setText(selectedNote.getTitle());
      messageField.setText(selectedNote.getMessage());
      noteListView.scrollTo(selectedIndex); // scroll up/down in list view if needed
    }
  }

  private void initTabView() {
    categoryTabPane.getSelectionModel().selectedItemProperty().addListener(
        (observableValue, tab, t1) -> {
          activeCategory = ((TabWithCategory) t1).getCategory();
          System.out.println(activeCategory.getName());
          System.out.println(activeCategory.getNotes());
          updateNoteListView(0);
        });
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

  private void updateCategoryTabView() {
    notebook.addCategory((List<Category>) notesDataAccess.getCategories());

    for (Category cat : notebook.getCategories()) {
      Tab categoryTab = tabSetText.createEditableTab(cat.getName(), cat);
      categoryTabPane.getTabs().add(categoryTab);
      activeCategory = cat;
    }
  }

  /**
   * Updates the content of the List View through the REST API. Selects and displays the note by the
   * given selectedIndex.
   *
   * @param selectedIndex The new index to select in the list view
   */
  private void updateNoteListView(int selectedIndex) {
    final Collection<Note> noteArray = notesDataAccess
        .getNotes(notebook.getGetCategoryIndex(activeCategory));
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

  public void setActiveCategory(Category category){
    this.activeCategory = activeCategory;
  }

  public Notebook getNotebook(){
    return this.notebook;
  }
}

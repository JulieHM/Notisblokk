package notisblokk.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.ToolBar;
import javafx.scene.web.HTMLEditor;
import notisblokk.core.Category;
import notisblokk.core.Note;

/**
 * FxAppController is communicating with the server through REST API. All data displayed in the GUI
 * is controlled by the API.
 */
public class FxAppController {


  @FXML
  private HTMLEditor messageField;

  @FXML
  private ListView<Note> noteListView;

  @FXML
  private Button saveNoteButton;

  @FXML
  private Button deleteNoteButton;

  @FXML
  private TabPane categoryTabPane;

  private TabSetText tabSetText = new TabSetText(this);

  private List<Category> categories = new ArrayList<>();

  private int activeCategoryIndex;

  private Category activeCategory;

  private NotesDataAccess notesDataAccess = new NotesDataAccess();

  /**
   * Initializes the graphical user interface. Set up custom cell factory and updates the List View
   * to display the initial notes loaded from the REST API.
   */
  @FXML
  public void initialize() {
    noteListView.setCellFactory(listView -> new NoteCell());
    setupHtmlEditor();
    categoryTabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
    updateCategoryTabView(false);
    initTabView();
  }

  private void setupHtmlEditor() {
    Node node = messageField.lookup(".top-toolbar");
    if (node instanceof ToolBar) {
      ToolBar bar = (ToolBar) node;
      bar.getItems().addAll(saveNoteButton, deleteNoteButton);
    }
  }

  private static String getText(String htmlText) {

    String result;

    Pattern pattern = Pattern.compile("<[^>]*>");
    Matcher matcher = pattern.matcher(htmlText);
    final StringBuffer text = new StringBuffer(htmlText.length());

    while (matcher.find()) {
      matcher.appendReplacement(
          text,
          " ");
    }

    matcher.appendTail(text);

    result = text.toString().trim();

    return result;
  }

  private String extractTitle(String message) {
    String[] title = getText(message).split(" ");
    return title[0];
  }


  /**
   * Add a new tab
   */
  @FXML
  private void onNewCategoryClick() {
    Note emptyNote = new Note("Empty note", "");
    Category newCategory = new Category("New category");
    newCategory.addNote(emptyNote);
    notesDataAccess.addCategory(newCategory);
    updateCategoryTabView(true);
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
    System.out.println("NEW NOTE CLICK");
    if (activeCategory == null) {
      messageField.setHtmlText("A category is needed to create a note.");
      return;
    }
    Note note = new Note("New note", "");
    notesDataAccess.addNote(activeCategoryIndex, note);
    int index = noteListView.getItems().size(); // current size will be the new index
    System.out.println("NEW INDEX: " + index);
    updateNoteListView(index);
  }

  /**
   * Event Handler for the Save button.
   */
  @FXML
  private void onSaveClick() {
    System.out.println("ON SAVE CLICK");
    int selectedIndex = noteListView.getSelectionModel().getSelectedIndex();
    Note selectedNote = notesDataAccess.getNote(activeCategoryIndex,
        selectedIndex);
    updateNoteInfo(selectedNote);
    notesDataAccess
        .updateNote(activeCategoryIndex, selectedIndex, selectedNote);
    updateNoteListView(selectedIndex);
  }

  /**
   * Event Handler for the Delete button.
   */
  @FXML
  private void onDeleteClick() {
    int selectedIndex = noteListView.getSelectionModel().getSelectedIndex();
    notesDataAccess.removeNote(activeCategoryIndex, selectedIndex);
    updateNoteListView(selectedIndex);
  }

  public void deleteCategory() {
    notesDataAccess.deleteCategory(activeCategoryIndex);
    updateCategoryTabView(false);
    updateNoteListView(categories.size() - 1);
  }

  /**
   * Renames the given category with the new name
   *
   * @param category category to be renamed
   * @param newName new name
   */
  public void renameCategory(Category category, String newName) {
    notesDataAccess.renameCategory(category, activeCategoryIndex);
  }

  /**
   * Displays the content of the note selected in the List View
   */
  private void displaySelectedNote() {
    int selectedIndex = noteListView.getSelectionModel().getSelectedIndex();
    Note selectedNote = notesDataAccess
        .getNote(activeCategoryIndex, selectedIndex);
    if (selectedNote != null) {
      messageField.setHtmlText(selectedNote.getMessage());
      noteListView.scrollTo(selectedIndex); // scroll up/down in list view if needed
    }
  }

  private void initTabView() {
    categoryTabPane.getSelectionModel().selectedItemProperty().addListener(
        (observableValue, tab, t1) -> {
          Category tempCategory;
          try {
            tempCategory = ((TabWithCategory) t1).getCategory();
          } catch (NullPointerException e) {
            return;
          }
          activeCategoryIndex = categories.indexOf(tempCategory);
          activeCategory = notesDataAccess.getCategory(activeCategoryIndex);
          updateNoteListView(0);
        });
  }

  /**
   * Updates the title, message and last edited date of the given note.
   *
   * @param note The note to be updated.
   */
  public void updateNoteInfo(Note note) {
    note.setLastEditedDate(); // sets it to current date/time
    note.setMessage(messageField.getHtmlText());
    note.setTitle(extractTitle(messageField.getHtmlText()));
  }

  /**
   * Updates the tab pane with new category-tabs.
   */
  private void updateCategoryTabView(boolean newCategory) {
    categoryTabPane.getTabs().clear();
    categories = ((List<Category>) notesDataAccess.getCategories());

    if (categories.size() < 1) {
      activeCategory = null;
      noteListView.setItems(null);
      messageField.setHtmlText("");
      return;
    }

    for (Category cat : categories) {
      Tab categoryTab = tabSetText.createEditableTab(cat.getName(), cat);
      categoryTabPane.getTabs().add(categoryTab);
    }
    if (newCategory) {
      categoryTabPane.getSelectionModel().select(categories.size() - 1);
      activeCategory = categories.get(categories.size() - 1);
    } else {
      activeCategory = categories.get(0);
    }
  }

  /**
   * Updates the content of the List View through the REST API. Selects and displays the note by the
   * given selectedIndex.
   *
   * @param selectedIndex The new index to select in the list view
   */
  public void updateNoteListView(int selectedIndex) {
    if (categories.size() < 1) {
      return;
    }
    final Collection<Note> noteArray = notesDataAccess.getNotes(activeCategoryIndex);
    System.out.println("NOTE ARRAY SIZE: " + noteArray.size());
    noteListView.setItems(FXCollections.observableArrayList(noteArray));
    if (noteArray.size() < 1) {
      messageField.setHtmlText("");
      return;
    }
    if (selectedIndex >= noteArray.size()) {
      selectedIndex = noteArray.size() - 1;
    }
    noteListView.getSelectionModel().select(selectedIndex);
    displaySelectedNote();
  }

  public void setActiveCategory(Category category) {
    this.activeCategory = category;
  }

  /**
   * USED TO ALLOW TESTING
   */
  public void setNotesDataAccess(final NotesDataAccess notesDataAccess) {
    this.notesDataAccess = notesDataAccess;
    initTabView();
    updateCategoryTabView(false);
  }
}

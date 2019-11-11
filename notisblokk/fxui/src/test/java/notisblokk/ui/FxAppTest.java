package notisblokk.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import notisblokk.core.Category;
import notisblokk.core.Note;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

public class FxAppTest extends ApplicationTest {

  @BeforeClass
  public static void headless() {
    if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
      System.setProperty("prism.verbose", "true"); // optional
      System.setProperty("java.awt.headless", "true");
      System.setProperty("testfx.robot", "glass");
      System.setProperty("testfx.headless", "true");
      System.setProperty("prism.order", "sw");
      System.setProperty("prism.text", "t2k");
      System.setProperty("testfx.setup.timeout", "2500");
    }
  }

  private FxAppController controller;
  private NotesDataAccess notesDataAccess = Mockito.mock(NotesDataAccess.class);
  private List<Category> categories = new ArrayList<>();

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FxApp.fxml"));
    Parent root = fxmlLoader.load();
    this.controller = fxmlLoader.getController();

    Scene scene = new Scene(root);

    setupMockData();

    stage.setTitle("Notisblokk");
    stage.setScene(scene);
    stage.show();

    stage.setResizable(false);
  }

  private void setupMockData() {
    /* Create test data */
    Note testNote = new Note("Test123", "<html dir=\"ltr\"><head></head><body contenteditable=\"true\">Test123</body></html>", LocalDateTime.now(), LocalDateTime.now());
    Note testNote2 = new Note("Test", "<html dir=\"ltr\"><head></head><body contenteditable=\"true\">Test</body></html>", LocalDateTime.now(), LocalDateTime.now());

    Category testCategory = new Category("Test category");
    testCategory.addNotes(testNote, testNote2);

    categories.add(testCategory);

    /* Create mock methods for notesDataAccess
     * If a method is used during a test it needs to have a mock counterpart to that runs instead.
     * Most have been implemented, but some remain. The idea is simply adding/removing/getting
     * the data from the "categories" in here rather than using API.
     */
    doAnswer(invocation -> categories).when(notesDataAccess).getCategories();

    doAnswer(invocation ->
     categories.get(invocation.getArgument(0))
    ).when(notesDataAccess).getCategory(anyInt());

    doAnswer(invocation ->
        categories.get(invocation.getArgument(0)).getNotes()
    ).when(notesDataAccess).getNotes(anyInt());

    doAnswer(invocation ->
        categories.get(invocation.getArgument(0)).getNote(invocation.getArgument(1))
    ).when(notesDataAccess).getNote(anyInt(), anyInt());

    // doAnswer(invocation -> ).when(notesDataAccess).updateNote(anyInt(), anyInt(), any(Note.class));

    doAnswer(invocation ->
        categories.get(invocation.getArgument(0)).addNote(invocation.getArgument(1))
    ).when(notesDataAccess).addNote(anyInt(), any(Note.class));

     doAnswer(invocation ->
         categories.add(invocation.getArgument(0))
         ).when(notesDataAccess).addCategory(any(Category.class));

    // doAnswer(invocation -> ).when(notesDataAccess).renameCategory(any(Category.class), anyInt());

    doAnswer(invocation -> {
      categories.get(invocation.getArgument(0)).removeNote((int) invocation.getArgument(1));
      return true;
    }).when(notesDataAccess).removeNote(anyInt(), anyInt());

     //oAnswer(invocation -> ).when(notesDataAccess).deleteCategory(anyInt());

    /* Reset GUI and override notesDataAccess */
    controller.setNotesDataAccess(notesDataAccess);
  }

  @Test
  public void testController() {
    Assert.assertNotNull(this.controller);
  }

  /**
   * Tests that noteListView contains the same elements as noteList
   */
  @Test
  public void testListView() {
    final ListView<Note> noteListView = lookup("#noteListView").query();
    /* should this category index be dynamic? */
    Assert.assertEquals(categories.get(0).getNotes(), noteListView.getItems());
  }

  /**
   * Test for checking that the title is being set correctly
   */
  @Test
  public void testTitle(){
    final ListView<Note> noteListView = lookup("#noteListView").query();

    clickOn("#newNote");
    clickOn("#messageField").write("title testtest test");
    clickOn("#saveNoteButton");

    Note selectedNote = noteListView.getSelectionModel().getSelectedItem();
    Assert.assertEquals("title", selectedNote.getTitle());
  }

  /**
   * Test for checking if the top element in listView is selected
   */
  @Test
  public void testSelected() {
    final ListView<Note> noteListView = lookup("#noteListView").query();
    Assert.assertEquals(0, noteListView.getSelectionModel().getSelectedIndex());
  }

  /**
   * Test for checking if the messageField contains the same message as the Note object
   */
  @Test
  public void testMessageField() {
    final HTMLEditor messageField = lookup("#messageField").query();
    Assert.assertEquals(categories.get(0).getNote(0).getMessage(), messageField.getHtmlText());
  }

  /**
   * Test for creating a new Note
   */
  @Test
  public void testNewNoteButton() {
    int previousSize = categories.get(0).getNumNotes();
    clickOn("#newNote");
    int newSize = categories.get(0).getNumNotes();
    Assert.assertEquals(previousSize + 1, newSize);
  }

  @Test
  public void testDeleteButton() {
    int previousSize = categories.get(0).getNumNotes();
    clickOn("#deleteNoteButton");
    int newSize = categories.get(0).getNumNotes();
    Assert.assertEquals(previousSize - 1, newSize);
  }

  @Test
  public void testNewCategoryButton() {
    int previousSize = categories.size();
    clickOn("#newCategory");
    int newSize = categories.size();
    Assert.assertEquals(previousSize + 1, newSize);
  }

}

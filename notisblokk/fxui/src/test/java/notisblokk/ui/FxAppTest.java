package notisblokk.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import notisblokk.json.NoteDeserializer;
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
  private List<Note> noteList;
  private List<Category> categoryList = new ArrayList<>();

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FxApp.fxml"));
    Parent root = fxmlLoader.load();
    this.controller = fxmlLoader.getController();

    Scene scene = new Scene(root);

    setupNotes();

    stage.setTitle("Notisblokk");
    stage.setScene(scene);
    stage.show();

    stage.setResizable(false);
  }

  private void setupNotes() {   //vil hente savedNotes
    System.out.println("SETUP NOTES");
    String categoriesApiResponse = "[{\"notes\":[{\"title\":\"Test123\",\"message\":\"\\u003chtml dir\\u003d\\\"ltr\\\"\\u003e\\u003chead\\u003e\\u003c/head\\u003e\\u003cbody contenteditable\\u003d\\\"true\\\"\\u003e\\u003cp\\u003eTest123\\u003c/p\\u003e\\u003c/body\\u003e\\u003c/html\\u003e\",\"lastEditedDate\":{\"date\":{\"year\":2019,\"month\":11,\"day\":7},\"time\":{\"hour\":15,\"minute\":33,\"second\":43,\"nano\":953820700}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":11,\"day\":7},\"time\":{\"hour\":15,\"minute\":32,\"second\":49,\"nano\":975265100}}},{\"title\":\"Test\",\"message\":\"\\u003chtml dir\\u003d\\\"ltr\\\"\\u003e\\u003chead\\u003e\\u003c/head\\u003e\\u003cbody contenteditable\\u003d\\\"true\\\"\\u003e\\u003cp\\u003eTest\\u003c/p\\u003e\\u003c/body\\u003e\\u003c/html\\u003e\",\"lastEditedDate\":{\"date\":{\"year\":2019,\"month\":11,\"day\":7},\"time\":{\"hour\":15,\"minute\":33,\"second\":57,\"nano\":262255700}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":11,\"day\":7},\"time\":{\"hour\":15,\"minute\":33,\"second\":44,\"nano\":835342900}}},{\"title\":\"New note\",\"message\":\"\",\"lastEditedDate\":{\"date\":{\"year\":2019,\"month\":11,\"day\":7},\"time\":{\"hour\":16,\"minute\":29,\"second\":50,\"nano\":572904700}},\"createdDate\":{\"date\":{\"year\":2019,\"month\":11,\"day\":7},\"time\":{\"hour\":16,\"minute\":29,\"second\":50,\"nano\":571906000}}}],\"name\":\"Test category\"}]";

    Note testNote = new Note("Test123", "<html dir=\"ltr\"><head></head><body contenteditable=\"true\">Test123</body></html>", LocalDateTime.now(), LocalDateTime.now());
    Note testNote2 = new Note("Test", "<html dir=\"ltr\"><head></head><body contenteditable=\"true\">Test</body></html>", LocalDateTime.now(), LocalDateTime.now());
    noteList = new ArrayList<>(List.of(testNote, testNote2));

    Category category = new Category("Test category");
    category.addNotes(noteList);
    categoryList.add(category);

    NoteDeserializer noteDeserializer = new NoteDeserializer();
    List<Category> catList = noteDeserializer.deserializeCategoriesFromString(categoriesApiResponse);

    when(notesDataAccess.getNote(anyInt(), anyInt()))
        .then(invocation -> noteList.get(invocation.getArgument(0)));
    when(notesDataAccess.getNotes(anyInt())).then(invocation -> noteList);

    when(notesDataAccess.getCategories()).then(invocation -> catList);
    when(notesDataAccess.getCategory(anyInt())).then(invocation -> catList.get(0));
    when(notesDataAccess.addCategory(any())).then(invocation -> true);

    controller.setNotesDataAccess(notesDataAccess);
    controller.printDebug();
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
    Assert.assertEquals(noteList, noteListView.getItems());
  }

  /**
   * Test for checking that the title is being set correctly
   */
  @Test
  public void testTitle(){
    final ListView<Note> noteListView = lookup("#noteListView").query();
    final HTMLEditor messageField = lookup("#messageField").query();

    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            messageField.setHtmlText("title testtest test");
          }
        }
    );
    Note note = noteListView.getSelectionModel().getSelectedItem();
    controller.updateNoteInfo(note);
    Assert.assertEquals("title", note.getTitle());
  }

  @Test
  public void testTitle2() {
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
  public void testMessageField() {  //tester å legge til note
    final HTMLEditor messageField = lookup("#messageField").query();
    Assert.assertEquals(noteList.get(0).getMessage(), messageField.getHtmlText());
  }

  @Test
  public void testNewNoteButtonPress() {
    clickOn("#newNote");
    final ListView<Note> noteListView = lookup("#noteListView").query();
    Assert.assertEquals(3, noteListView.getItems().size());
  }
}

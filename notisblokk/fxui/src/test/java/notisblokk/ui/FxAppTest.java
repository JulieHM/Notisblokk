package notisblokk.ui;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
  private List<Note> noteList;

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
    Note testNote = new Note("Test123", "Test123", LocalDateTime.now(), LocalDateTime.now());
    Note testNote2 = new Note("Test", "Test", LocalDateTime.now(), LocalDateTime.now());
    noteList = new ArrayList<>(List.of(testNote, testNote2));

    when(notesDataAccess.getNote(anyInt()))
        .then(invocation -> noteList.get(invocation.getArgument(0)));
    when(notesDataAccess.getNotes()).then(invocation -> noteList);
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
    Assert.assertEquals(noteList, noteListView.getItems());
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
    final TextArea messageField = lookup("#messageField").query();
    Assert.assertEquals(noteList.get(0).getMessage(), messageField.getText());

  }

  /**
   * Test for checking if the titleField contains the same title as the Note object
   */
  @Test
  public void testTitleField() {
    final TextField titleField = lookup("#titleField").query();
    Assert.assertEquals(noteList.get(0).getTitle(), titleField.getText());
  }
}

package notisblokk.ui;

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
import notisblokk.core.Notes;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


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
  private Note note = mock(Note.class);
  private Notes savedNotes = Mockito.mock(Notes.class);
  private List<Note> noteList;
  //private ListView<Note> noteListView = Mockito.mock(ListView.class);

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



  public void setupNotes(){   //vil hente savedNotes
    Note testNote = new Note("Test123","Test123", LocalDateTime.now(), LocalDateTime.now());
    Note testNote2 = new Note("Test","Test", LocalDateTime.now(), LocalDateTime.now());
    noteList = new ArrayList<Note>(List.of(testNote, testNote2));

    when(savedNotes.getNote(anyInt())).then(invocation -> noteList.get(invocation.getArgument(0)));
    when(savedNotes.getNumNotes()).then(invocation -> noteList.size());
    when(savedNotes.iterator()).then(invocation -> noteList.iterator());
    //when(noteListView.getItems()).then(invocation -> noteList);
    controller.setSavedNotes(savedNotes);
  }


  @Test
  public void testController() {
    Assert.assertNotNull(this.controller);
  }

  /** Tests that noteListView contains the same elements as noteList */
  @Test
  public void testListView(){
    final ListView<Note> noteListView = lookup("#noteListView").query();
    Assert.assertEquals(noteList, noteListView.getItems()); //ser om savednotes inneholder de samme objektene som noteListView
  }

  /** Test that the top element in listView is selected */
  @Test
  public void testSelected(){
    final ListView<Note> noteListView = lookup("#noteListView").query();
    Assert.assertEquals(0,noteListView.getSelectionModel().getSelectedIndex());
  }


  @Test
  public void testMessageField() {  //tester å legge til note
    final TextArea messageField = lookup("#messageField").query();
    Assert.assertEquals(messageField.getText(), noteList.get(0).getMessage());

  }

  @Test
  public void testTitleField(){
    final TextField titleField = lookup("#titleField").query();
    Assert.assertEquals(titleField.getText(), noteList.get(0).getTitle());
  }



  //@Test
  //public void testDeleteNote() {
 // }


}

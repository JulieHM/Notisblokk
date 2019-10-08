package notisblokk.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;
import static org.mockito.Mockito.mock;


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
  private Notes savedNotes = Mockito.spy(Notes.class); //hvordan koble opp mot savedNotes

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FxApp.fxml"));
    Parent root = fxmlLoader.load();
    this.controller = fxmlLoader.getController();
    Scene scene = new Scene(root);

    //setupNotes();

    stage.setTitle("Notisblokk");
    stage.setScene(scene);
    stage.show();

    stage.setResizable(false);
  }



  //public void setupNotes(){   //vil hente savedNotes
    //List<Note> notes = new ArrayList<>();
    //Note testNote = new Note("Test123","Test123", LocalDateTime.now(), LocalDateTime.now());
    //when(savedNotes.addNote(any(Note.class))).then(invocation -> {
     // notes.add(invocation.getArgument(0,Notes.class));
  //  return notes;
  //  });

   // }


  @Test
  public void testController() {
    Assert.assertNotNull(this.controller);
    System.out.println(savedNotes);
  }

  @Test
  public void testListView(){
    final ListView<?> noteListView = lookup("#noteListView").query();
    Assert.assertEquals(savedNotes, noteListView.getItems()); //ser om savednotes inneholder de samme objektene som noteListView
    Assert.assertEquals(0,noteListView.getSelectionModel().getSelectedIndex());

  }




  //@Test
  //public void testAddNote() {  //tester å legge til note
    //final Button button = lookup("#newNote").query();   //henter id til button
    //final TextArea messageField = lookup("#messageField").query();
    //this.controller.onNewNoteClick();

   // savedNotes.addNote(note);
    //ser om antall notes er 1 når testen har kjørt
   // assertNotNull(savedNotes.getNumNotes());
    //Assert.assertEquals(1, savedNotes.getNumNotes());
    //assertEquals(messageField, note.getMessage());
   // FxAssert.verifyThat("#messageField", hasText(note.getMessage()+"")); //sjekker at tekstfeltet er det samme som note sitt tekstfelt
    //FxAssert.verifyThat("#titleField", hasText(note.getTitle()));  //sjekker at tittlen samsvarer med note sin tittel
 // }

  //@Test
  //public void testNoteListView() {
    //final ListView noteListView = lookup("#noteListView").query();
    //assertEquals(savedNotes, noteListView.getItems());  //ser om savedNotes inneholder samme som listview
    //assertEquals(0, noteListView.getSelectionModel().getSelectedIndex()); //ser om øverste el er selected
  //}


  //@Test
  //public void testDeleteNote() {
 // }


}

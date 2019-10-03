package ui;

import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import notisblokk.ui.FxAppController;
import notisblokk.ui.NoteCell;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TableViewMatchers;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.


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
  private Note note = Mockito.mock(Note.class);
  private Notes savedNotes = Mockito.mock(Notes.class);  //hvordan koble opp mot savedNotes
  //private Notes savedNotes = new Notes();  //trenger vi å lage denne ettersom vi allerede har tatt med controlleren hvor den opprettes

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FxApp.fxml"));
    Parent root = fxmlLoader.load();
    this.controller = fxmlLoader.load();
    Scene scene = new Scene(root);
    setupNotes();
    stage.setTitle("Notisblokk");
    stage.setScene(scene);
    stage.show();

    stage.setResizable(false);
  }


  public void setupNotes(){   //vil hente savedNotes
    when(savedNotes.addNote(any(Note.class)))
        .then(invocation -> savedNotes.addNote(note));
  }

  @Test
  public void testController() {
    Assert.assertNotNull(this.controller);
  }


  @Test
  public void testAddNote() {  //tester å legge til note
    final Button button = lookup("#newNote").query();   //henter id til button
    clickOn(button);
    //ser om antall notes er 1 når testen har kjørt
    assertEquals(1, savedNotes.getNumNotes());
    //FxAssert.verifyThat("#messageField", TableViewMatchers.containsRow(note.getMessage())); //sjekker at tekstfeltet er det samme som note sitt tekstfelt
    //FxAssert.verifyThat("#titleField", TableViewMatchers.containsRow(note.getTitle()));  //sjekker at tittlen samsvarer med note sin tittel
  }

  @Test
  public void testNoteListView() {
    final ListView noteListView = lookup("#noteListView").query();
    assertEquals(savedNotes, noteListView.getItems());  //ser om savedNotes inneholder samme som listview
    Assert.assertEquals(0, noteListView.getSelectionModel().getSelectedIndex()); //ser om øverste el er selected
  }



  @Test
  public void testDeleteNote() {
  }


}

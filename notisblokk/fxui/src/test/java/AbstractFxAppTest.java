import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import notisblokk.ui.FxAppController;
import notisblokk.ui.NoteCell;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public abstract class AbstractFxAppTest extends ApplicationTest {

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
  private Note note;
  private Notes notes;
  private NoteCell noteCell;
  private Notes savedNotes = new Notes();

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FxApp.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);

    stage.setTitle("Notisblokk");
    stage.setScene(scene);
    stage.show();

    stage.setResizable(false);
  }




  @Test
  public void testLocationListView() {
  }

  @Test
  public void testAddNote() {
  }

  @Test
  public void testDeleteNote() {
  }

  }

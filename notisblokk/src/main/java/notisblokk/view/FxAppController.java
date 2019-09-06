package notisblokk.view;

import java.awt.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notisblokk.model.Note;

public class FxAppController {

  @FXML
  private Button saveNote;

  @FXML
  private Button newNote;

  @FXML
  private TextField titleField;

  @FXML
  private TextArea noteText;

  @FXML
  private VBox noteContainer;

  @FXML
  private HBox singleNoteContainer;

  @FXML
  private Label savedNote;

  @FXML
  private void onNewNoteClick() {
    titleField.setText("");
    noteText.setText("");

    Label label = new Label("New note");

    HBox hbox = new HBox(label);
    noteContainer.getChildren().add(hbox);
  }
  @FXML
  private void onSaveClick() {

  }

}

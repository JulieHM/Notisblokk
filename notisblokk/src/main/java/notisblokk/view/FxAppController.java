package notisblokk.view;

import java.awt.Insets;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notisblokk.model.Note;
import notisblokk.model.Notes;

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

 private Notes savedNotes = new Notes();
 private ArrayList<Label> labelList = new ArrayList<>();
 private int activeNoteIndex;

  @FXML
  public void initialize() {

  }

  @FXML
  private void onNewNoteClick() {
    titleField.setText("");
    noteText.setText("");

    Note note = new Note(null, null);
    savedNotes.addNote(note);

    Label label = new Label("New note");
    label.setPrefHeight(30);
    label.setPrefWidth(100);
    label.setAlignment(Pos.CENTER);
    label.setStyle("-fx-border-style: solid; -fx-border-width: 0.2; -fx-border-radius: 3");
    labelList.add(label);

    label.setOnMouseClicked(event -> {
      activeNoteIndex = labelList.indexOf(label);
      Note activeNote = savedNotes.getNote(activeNoteIndex);
      titleField.setText(activeNote.getTitle());
      noteText.setText(activeNote.getMessage());
      });


    noteContainer.getChildren().add(label);
  }

  @FXML
  private void onSaveClick() {

  }

}

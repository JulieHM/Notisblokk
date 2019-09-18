package notisblokk.ui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import notisblokk.json.NoteDeserializer;
import notisblokk.json.NoteSerializer;

public class FxAppController {

  @FXML
  private TextField titleField;

  @FXML
  private TextArea noteText;

  @FXML
  private ListView noteContainer;

  private ObservableList<Label> observableList = FXCollections.observableArrayList(); //SKRIV DENNE
  //ListView<Label> listView = new ListView<Label>();
  private Notes savedNotes = new Notes();
  private ArrayList<Label> labelList = new ArrayList<>();
  private int activeNoteIndex;
  private static final String SAVE_PATH = System.getProperty("user.home")
      + "/.projectNotes/notes.json"; // Should use the static one instead?

  @FXML
  public void initialize() {
    NoteDeserializer noteDeserializer = new NoteDeserializer();
    try {
      savedNotes.addNotes(noteDeserializer.deserializeNotes(SAVE_PATH));
    } catch (IOException e) {
      System.err.println("Unable to deserialize notes from json.");
    }
    for (Note note : savedNotes) {
      addLabel(note.getTitle());
    }
  }

  private void addLabel(String title) {
    Label label = new Label(title);
    label.setPrefHeight(30);
    label.setPrefWidth(100);
    label.setAlignment(Pos.CENTER);
    label.setStyle("-fx-border-style: solid; -fx-border-width: 0.2; -fx-border-radius: 3");

    labelList.add(label);
    activeNoteIndex = labelList.indexOf(label);

    label.setOnMouseClicked(event -> {
      activeNoteIndex = labelList.indexOf(label);
      Note activeNote = savedNotes.getNote(activeNoteIndex);
      titleField.setText(activeNote.getTitle());
      noteText.setText(activeNote.getMessage());
      updateActiveNote(activeNoteIndex);
    });


    observableList.add(label);
    noteContainer.setItems(observableList);

    updateActiveNote(activeNoteIndex);
  }

  /**
   * Updates the background color of all labels. Coloring the currently active while removing color
   * from inactive labels.
   *
   * @param activeNoteIndex Index of the currently active note label.
   */
  private void updateActiveNote(int activeNoteIndex) {
    for (int i = 0; i < labelList.size(); i++) {
      if (i != activeNoteIndex) {
        labelList.get(i).setStyle("-fx-background-color: none");
      } else {
        labelList.get(i).setStyle("-fx-background-color: green");
      }
    }
  }

  @FXML
  private void onNewNoteClick() {
    titleField.setText("");
    noteText.setText("");

    Note note = new Note(null, null);
    savedNotes.addNote(note);
    addLabel("New note");
  }

  @FXML
  private void onSaveClick() {
    Note activeNote = savedNotes.getNote(activeNoteIndex);
    labelList.get(activeNoteIndex).setText(titleField.getText());
    activeNote.setTitle(titleField.getText());
    activeNote.setMessage(noteText.getText());
    saveNotesToJson();
  }

  private void saveNotesToJson() {
    NoteSerializer noteSerializer = new NoteSerializer();
    try {
      /* why is a path needed if there's a static one in the class? */
      noteSerializer.serializeNotes(savedNotes.getNotes(), SAVE_PATH);
    } catch (IOException e) {
      System.err.println("Unable to save notes to json.");
    }
  }
}

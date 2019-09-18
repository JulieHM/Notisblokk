package notisblokk.ui;

import javafx.scene.control.ListCell;
import notisblokk.core.Note;

public class NoteCell extends ListCell<Note> {

  @Override
  public void updateItem(Note note, boolean empty) {
    super.updateItem(note, empty);

    if (empty || note == null) {
      this.setText(null);
    } else {
      this.setText(note.getTitle());
    }
  }

}

package notisblokk.json;

import java.io.File;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import notisblokk.core.Note;
import com.google.gson.Gson;
import java.io.FileNotFoundException;

public class NoteSerializer {

  private Gson gsonSerializer;

  /**
   * The default constructor for creating a NoteSerializer object.
   */
  public NoteSerializer() {
    gsonSerializer = new Gson();
  }

  /**
   * Takes in a list of notes and serializes them to a notisblokk.json-formatted string and saves
   * them locally.
   *
   * @param noteList list of all notes
   * @return true if the action was completed
   */
  public boolean serializeNotes(List<Note> noteList, String path) throws IOException {
    String json = gsonSerializer.toJson(noteList);

    File file = new File(path);
    if (!file.exists()) {
      file.getParentFile().mkdirs();
    }

    try (FileWriter writer = new FileWriter(new File(path))) {
      writer.write(json);
      return true;
    } catch (FileNotFoundException e) {
      System.err.println("FileNotFound");
      return false;
    }
  }

}

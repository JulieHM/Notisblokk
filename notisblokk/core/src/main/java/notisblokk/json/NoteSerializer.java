package notisblokk.json;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import notisblokk.core.Note;

public class NoteSerializer {

  private Gson gsonSerializer;

  /**
   * The default constructor for creating a NoteSerializer object.
   */
  public NoteSerializer() {
    gsonSerializer = new Gson();
  }

  /**
   * Takes in a list of notes and serializes them to a notisblokk.json-formatted string
   * and saves them locally.
   *
   * @param noteList list of all notes
   * @return true if the action was completed
   */
  public boolean serializeNotes(List<Note> noteList, String path) throws IOException {
    /*
    TODO: Should probably make it possible to append notes to the notisblokk.json file
          instead of rewriting the whole file every time
    */
    String json = gsonSerializer.toJson(noteList);
    try (FileWriter writer = new FileWriter(new File(path))) {
      writer.write(json);
      return true;
    } catch (FileNotFoundException e) {
      System.err.println("FileNotFound");
      return false;
    }
  }

}

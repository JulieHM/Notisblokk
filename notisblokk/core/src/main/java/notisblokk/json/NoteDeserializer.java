package notisblokk.json;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import notisblokk.core.Note;

public class NoteDeserializer {

  private Gson gsonDeserializer;

  /**
   * The default constructor for creating a NoteDeserializer object.
   */
  public NoteDeserializer() {
    gsonDeserializer = new Gson();
  }

  /**
   * Takes a local path to a file of json-formatted Note objects. Builds the Note objects
   * found in the file and returns them as a List of Note.
   *
   * @param path The path to the json-formatted file.
   * @return A List of Note objects.
   */
  public List<Note> deserializeNotes(String path) throws IOException {
    if (Files.exists(Paths.get(path))) {
      String jsonFromFile = Files.readString(Paths.get(path));
      // Could probably improve the way this is done
      Note[] noteArray = gsonDeserializer.fromJson(jsonFromFile, Note[].class);
      List<Note> noteList = new ArrayList<>();
      Collections.addAll(noteList, noteArray);
      return noteList;
    }

    return new ArrayList<>();
  }

}

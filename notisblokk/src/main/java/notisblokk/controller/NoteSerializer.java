package notisblokk.controller;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import notisblokk.model.Note;

public class NoteSerializer {

  private static final String SAVE_PATH =
      System.getProperty("user.home") + "/.projectNotes/notes.json";

  private Gson gsonSerializer;

  /**
   * The default constructor for creating a NoteSerializer object.
   */
  public NoteSerializer() {
    gsonSerializer = new Gson();
  }

  /**
   * Takes in a list of notes and serializes them to a json-formatted string and saves them
   * locally.
   *
   * @param noteList list of all notes
   * @return true if the action was completed
   */
  public boolean serializeNotes(List<Note> noteList, String path) throws IOException {
    //TODO: Should probably make it possible to append notes to the json file
    // instead of rewriting the whole file every time
    String json = gsonSerializer.toJson(noteList);
    FileWriter writer;
    if (Files.exists(Paths.get(path))) {
      writer = new FileWriter(path);
      writer.write(json);
    } else {
      File jsonFile = new File(SAVE_PATH);
      if (!jsonFile.getParentFile().mkdirs()) {
        return false;
      }
      Files.setAttribute(Paths.get(jsonFile.getParent()), "dos:hidden", true);
      if (!jsonFile.createNewFile()) {
        return false;
      }
      writer = new FileWriter(path);
      writer.write(json);
    }

    writer.close();
    return true;
  }

}

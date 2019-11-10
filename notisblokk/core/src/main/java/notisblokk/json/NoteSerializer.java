package notisblokk.json;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import notisblokk.core.Category;
import notisblokk.core.Note;
import notisblokk.core.Notebook;

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
  public boolean serializeNotesToLocal(List<Note> noteList, String path) throws IOException {
    String json = gsonSerializer.toJson(noteList);

    File file = new File(path);
    if (!file.exists()) {
      file.getParentFile().mkdirs();
    }

    try (Writer writer = new OutputStreamWriter(
        new FileOutputStream(file), StandardCharsets.UTF_8)) {
      writer.write(json);
      return true;
    } catch (FileNotFoundException e) {
      System.err.println("FileNotFound");
      return false;
    }
  }

  public boolean serializeNotebookToLocal(Notebook notebook, String path)
      throws IOException {
    String json = gsonSerializer.toJson(notebook);

    File file = new File(path);
    if (!file.exists()) {
      file.getParentFile().mkdirs();
    }

    try (Writer writer = new OutputStreamWriter(
        new FileOutputStream(file), StandardCharsets.UTF_8)) {
      writer.write(json);
      return true;
    } catch (FileNotFoundException e) {
      System.err.println("FileNotFound");
      return false;
    }
  }

  /**
   * Returns a list of notes as a string.
   */
  public String serializeNotesToString(List<Note> noteList) {
    return gsonSerializer.toJson(noteList);
  }

  public String serializeCategoryToString(Category category){
    return gsonSerializer.toJson(category);
  }

  /**
   * Returns the parameter note as a string.
   */
  public String serializeNoteToString(Note note) {
    return gsonSerializer.toJson(note);
  }

}

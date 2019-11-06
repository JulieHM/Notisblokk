package notisblokk.json;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import notisblokk.core.Category;
import notisblokk.core.Note;
import notisblokk.core.Notebook;

public class NoteDeserializer {

  private Gson gsonDeserializer;

  /**
   * The default constructor for creating a NoteDeserializer object.
   */
  public NoteDeserializer() {
    gsonDeserializer = new Gson();
  }

  /**
   * Deserializes a local notebook. If a list of notes is found instead, a category with those
   * notes will be created, added to a notebook which is then returned.
   * @param path of the local notes
   * @return a notebook with the locally stored categories and notes
   * @throws IOException if path error or similar
   */
  public Notebook deserializeLocalNotebook(String path) throws IOException {
    if (Files.exists(Paths.get(path))) {
      String jsonFromFile = Files.readString(Paths.get(path));
      Notebook notebook;
      try {
        notebook = gsonDeserializer.fromJson(jsonFromFile, Notebook.class);
        return notebook;
      } catch (Exception e) {
        Note[] noteList = gsonDeserializer.fromJson(jsonFromFile, Note[].class);
        List<Category> catList = new ArrayList<>();
        catList.add(new Category("New category", Arrays.asList(noteList)));
        notebook = new Notebook(catList);
        return notebook;
      }
    }
    return new Notebook();
  }

  /**
   * Takes a string in json format, and deserializes into notes.
   */
  public List<Note> deserializeNotesFromString(String notesFromString) {
    Note[] notes = gsonDeserializer.fromJson(notesFromString, Note[].class);
    if (notes != null) {
      return Arrays.asList(notes);
    }

    Note[] notes1 = new Note[]{new Note("Empty note", "")};
    return Arrays.asList(notes1);
  }

  /**
   * Takes a string in json format and deserializes into categories
   */
  public List<Category> deserializeCategoriesFromString(String categoriesFromString) {
    Category[] categories = gsonDeserializer.fromJson(categoriesFromString, Category[].class);
    return Arrays.asList(categories);
  }

  public Category deserializeCategoryFromString(String categoryFromString) {
    return gsonDeserializer.fromJson(categoryFromString, Category.class);
  }

  /**
   * Takes a string in json format, deserializes it and returns it as a note.
   */
  public Note deserializeNoteFromString(String noteFromString) {
    System.out.println(noteFromString);
    return gsonDeserializer.fromJson(noteFromString, Note.class);
  }
}

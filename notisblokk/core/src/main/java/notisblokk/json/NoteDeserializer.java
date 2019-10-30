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
   * Takes a local path to a file of notisblokk.json-formatted Note objects. Builds the Note objects
   * found in the file and returns them as a List of Note.
   *
   * @param path The path to the notisblokk.json-formatted file.
   * @return A List of Note objects.
   */
  public List<Note> deserializeLocalNotes(String path) throws IOException {
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

  public List<Category> deserializeLocalCategories(String path) throws IOException {
    if (Files.exists(Paths.get(path))) {
      String jsonFromFile = Files.readString(Paths.get(path));
      // Could probably improve the way this is done
      Category[] categoryArray = gsonDeserializer.fromJson(jsonFromFile, Category[].class);
      List<Category> categoryList = new ArrayList<>();
      Collections.addAll(categoryList, categoryArray);
      for (var category : categoryList) {
        System.out.println(category.getName());
      }
      return categoryList;
    }
    return null;
  }

  public Notebook deserializeLocalNotebook(String path) throws IOException {
    if (Files.exists(Paths.get(path))) {
      String jsonFromFile = Files.readString(Paths.get(path));
      return gsonDeserializer.fromJson(jsonFromFile, Notebook.class);
    }
    return null;
  }

  /**
   * Takes a string in json format, and deserializes into notes.
   */
  public List<Note> deserializeNotesFromString(String notesFromString) {
    Note[] notes = gsonDeserializer.fromJson(notesFromString, Note[].class);
    return Arrays.asList(notes);
  }

  /**
   * Takes a string in json format and deserializes into categories
   */
  public List<Category> deserializeCategoriesFromString(String categoriesFromString) {
    Category[] categories = gsonDeserializer.fromJson(categoriesFromString, Category[].class);
    return Arrays.asList(categories);
  }

  /**
   * Takes a string in json format, deserializes it and returns it as a note.
   */
  public Note deserializeNoteFromString(String noteFromString) {
    return gsonDeserializer.fromJson(noteFromString, Note.class);
  }
}

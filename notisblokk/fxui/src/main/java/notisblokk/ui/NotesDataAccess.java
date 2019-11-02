package notisblokk.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Collections;
import notisblokk.core.Category;
import notisblokk.core.Note;
import notisblokk.json.NoteDeserializer;
import notisblokk.json.NoteSerializer;

public class NotesDataAccess {

  private final NoteDeserializer noteDeserializer = new NoteDeserializer();
  private final NoteSerializer noteSerializer = new NoteSerializer();
  private final String baseUrl = "http://localhost:8080/notes"; // TODO: make it a cmd argument?

  private URI buildRequestUri(String path) {
    try {
      return new URI(baseUrl + path);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * http://localhost:8080/notes/category/
   */
  public Collection<Category> getCategories() {
    System.out.println("GET http://localhost:8080/notes");
    final URI requestUri = buildRequestUri(""); // baseUrl only
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .GET()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      return noteDeserializer.deserializeCategoriesFromString(response.body());
    } catch (IOException | InterruptedException e) {
      return Collections.emptyList();
    }
  }

  /**
   * http://localhost:8080/notes/category/
   */
  public Category getCategory(int categoryIndex) {
    System.out.println("GET http://localhost:8080/notes/category/" + categoryIndex);
    final URI requestUri = buildRequestUri("/category/" + categoryIndex);
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .GET()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      return noteDeserializer.deserializeCategoryFromString(response.body());
    } catch (IOException | InterruptedException e) {
      return null;
    }
  }

  /**
   * http://localhost:8080/notes/category/{catIndex}/notes GET
   */
  public Collection<Note> getNotes(int categoryIndex) {
    System.out.println("GET http://localhost:8080/notes/category/" + categoryIndex + "/notes");
    final URI requestUri = buildRequestUri("/category/" + categoryIndex + "/notes");
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .GET()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      return noteDeserializer.deserializeNotesFromString(response.body());
    } catch (IOException | InterruptedException e) {
      return Collections.emptyList();
    }
  }

  /**
   * http://localhost:8080/notes/category/{catIndex}/notes/{index} GET
   */
  public Note getNote(int catIndex, int index) {
    System.out.println("GET http://localhost:8080/notes/category/" + catIndex + "/notes/" + index);
    final URI requestUri = buildRequestUri("/category/" + catIndex + "/notes/" + index);
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .GET()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      return noteDeserializer.deserializeNoteFromString(response.body());
    } catch (IOException | InterruptedException e) {
      return null;
    }
  }

  /**
   * http://localhost:8080/notes/{index}
   *
   * @param note POST
   */
  public void updateNote(int categoryIndex, int index, Note note) {
    System.out.println("POST http://localhost:8080/notes/" + index + "\n\t-> " + note.toString());
    final URI requestUri = buildRequestUri("/category/" + categoryIndex + "/notes/" + index);
    postNoteRequest(requestUri, note);
  }

  /**
   * http://localhost:8080/notes POST
   */
  public void addNote(int categoryIndex, Note note) {
    System.out.println(
        "POST http://localhost:8080/notes/category/" + categoryIndex + "/notes\n\t-> " + note
            .toString());
    final URI requestUri = buildRequestUri("/category/" + categoryIndex + "/notes");
    postNoteRequest(requestUri, note);
  }

  /**
   * Add category
   *
   * @param category
   */
  public void addCategory(Category category) {
    System.out.println("POST http://localhost:8080/notes/category");
    final URI requestUri = buildRequestUri("/category");
    updateCategory(category, requestUri);
  }

  public void renameCategory(Category category, int index) {
    System.out.println("POST http://localhost:8080/notes/category/" + index);
    final URI requestUri = buildRequestUri("/category/" + index);
    updateCategory(category, requestUri);
  }

  public void updateCategory(Category category, URI requestUri) {
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(noteSerializer.serializeCategoryToString(category)))
        .build();
    try {
      final HttpResponse<InputStream> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofInputStream());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * HELPER METHOD FOR NOTE POST REQUESTS
   */
  private void postNoteRequest(URI requestUri, Note note) {
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(noteSerializer.serializeNoteToString(note)))
        .build();
    try {
      final HttpResponse<InputStream> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofInputStream());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * http://localhost:8080/notes/{index} DELETE
   */
  public void removeNote(int categoryIndex, int index) {
    System.out.println(
        "DELETE http://localhost:8080/notes/category/" + categoryIndex + "/notes/" + index);
    final URI requestUri = buildRequestUri("/category/" + categoryIndex + "/notes/" + index);
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .DELETE()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println("\t-> Response: " + response.body());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteCategory(int index) {
    System.out.println("DELETE http://localhost:8080/notes/category" + index);
    final URI requestUri = buildRequestUri("/category/" + index);
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .DELETE()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println("\t-> Response: " + response.body());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}

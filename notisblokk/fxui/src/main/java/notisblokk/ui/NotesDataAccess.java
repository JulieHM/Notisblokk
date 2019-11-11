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

  private URI buildRequestUri(String path) {
    try {
      String baseUrl = "http://localhost:8080/categories";
      return new URI(baseUrl + path);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * http://localhost:8080/categories GET
   */
  Collection<Category> getCategories() {
    System.out.println("GET CATEGORIES");
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
   * http://localhost:8080/categories/{categoryIndex} GET
   */
  Category getCategory(int categoryIndex) {
    System.out.println("GET CATEGORY");
    final URI requestUri = buildRequestUri("/" + categoryIndex);
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
   * http://localhost:8080/categories/{catIndex}/notes GET
   */
  public Collection<Note> getNotes(int categoryIndex) {
    final URI requestUri = buildRequestUri("/" + categoryIndex + "/notes");
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
   * http://localhost:8080/categories/{catIndex}/notes/{index} GET
   */
  public Note getNote(int catIndex, int index) {
    final URI requestUri = buildRequestUri("/" + catIndex + "/notes/" + index);
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
   * http://localhost:8080/categories/{categoryIndex}/notes/{notes} POST
   * @param note POST
   */
  void updateNote(int categoryIndex, int index, Note note) {
    final URI requestUri = buildRequestUri("/" + categoryIndex + "/notes/" + index);
    postNoteRequest(requestUri, note);
  }

  /**
   * http://localhost:8080/categories/{categoryIndex}/notes POST
   */
  void addNote(int categoryIndex, Note note) {
    final URI requestUri = buildRequestUri("/" + categoryIndex + "/notes");
    postNoteRequest(requestUri, note);
  }

  /**
   * Helper method for category POST
   *
   * @param category to be added
   */
  boolean addCategory(Category category) {
    System.out.println("ADD CATEGORY");
    final URI requestUri = buildRequestUri("");
    updateCategory(category, requestUri);
    return true;
  }

  /**
   * Helper method for category POST
   *
   * @param category to replace with
   */
  void renameCategory(Category category, int index) {
    System.out.println("RENAME CATEGORY");
    final URI requestUri = buildRequestUri("/" + index);
    updateCategory(category, requestUri);
  }

  /**
   *
   * @param category
   * @param requestUri
   */
  private void updateCategory(Category category, URI requestUri) {
    System.out.println("UPDATE CATEGORY");
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
   * http://localhost:8080/{index} DELETE
   */
  void removeNote(int categoryIndex, int index) {
    final URI requestUri = buildRequestUri("/" + categoryIndex + "/notes/" + index);
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .DELETE()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  void deleteCategory(int index) {
    final URI requestUri = buildRequestUri("/" + index);
    final HttpRequest request = HttpRequest.newBuilder(requestUri)
        .header("Accept", "application/json")
        .DELETE()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}

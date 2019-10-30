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
    System.out.println("GET http://localhost:8080/notes/category");
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
   * http://localhost:8080/notes/category/{catIndex}/notes GET
   */
  public Collection<Note> getNotes() {
    System.out.println("GET http://localhost:8080/notes/category/{catIndex}/notes");
    final URI requestUri = buildRequestUri(""); // baseUrl only
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
   * http://localhost:8080/notes/{index} GET
   */
  public Note getNote(int index) {
    System.out.println("GET http://localhost:8080/notes/" + index);
    final URI requestUri = buildRequestUri("/" + index);
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
  public void updateNote(int index, Note note) {
    System.out.println("POST http://localhost:8080/notes/" + index + "\n\t-> " + note.toString());
    final URI requestUri = buildRequestUri("/" + index); // baseUrl only
    postNoteRequest(requestUri, note);
  }

  /**
   * http://localhost:8080/notes POST
   */
  public void addNote(Note note) {
    System.out.println("POST http://localhost:8080/notes\n\t-> " + note.toString());
    final URI requestUri = buildRequestUri(""); // baseUrl only
    postNoteRequest(requestUri, note);
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
  public void removeNote(int index) {
    System.out.println("DELETE http://localhost:8080/notes/" + index);
    final URI requestUri = buildRequestUri("/" + index);
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

package notisblokk.ui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import notisblokk.json.NoteDeserializer;
import notisblokk.json.NoteSerializer;

public class NotesDataAccess {
  private NoteDeserializer noteDeserializer = new NoteDeserializer();
  private NoteSerializer noteSerializer = new NoteSerializer();

  /**
   *   public Collection<Note> getAllNotes() {
   *     final URI requestUri = URI.create("http://localhost:8080/notes");
   *     final HttpRequest request = HttpRequest.newBuilder(requestUri)
   *         .header("Accept", "application/json")
   *         .GET()
   *         .build();
   *     try {
   *       final HttpResponse<String> response = HttpClient.newBuilder()
   *           .build()
   *           .send(request, HttpResponse.BodyHandlers.ofString());
   *       final String responseString = response.body();
   *       System.out.println(responseString);
   *       System.out.println(responseString);
   *     } catch (IOException | InterruptedException e) {
   *     }
   *     return Collections.emptyList();
   *   }
   */
  public List<Note> getNotes() {
      // TODO implement the method
      final URI requestUri = URI.create("http://localhost:8080/notes");
      final HttpRequest request = HttpRequest.newBuilder(requestUri)
          .header("Accept", "application/json")
          .GET()
          .build();
      try {
        final HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
        final String responseString = response.body();
        List<Note> notes = noteDeserializer.deserializeNotesFromString(responseString);
        return notes;
      } catch (IOException | InterruptedException e) {
        return null;
      }
  }

  /**
   * http://localhost:8080/notes/{index}
   * GET
   */
  public Note getNote(int index) {
    // TODO implement the method
    Note note = new Note("test", "test"); // from restserver
    return note;
  }

  /**
   * http://localhost:8080/notes/{index}
   * @param index
   * @param note
   * POST
   */
  public void updateNote(int index, Note note) {
    // TODO implement the method
  }

  /**
   * http://localhost:8080/notes
   * POST
   */
  public void addNote(Note note) {
    // TODO implement the method
  }

  /**
   * http://localhost:8080/notes/{index}
   * DELETE
   */
  public void removeNote(int index) {
    // TODO implement the method
  }
}

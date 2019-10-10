package notisblokk.ui;

import notisblokk.core.Note;
import notisblokk.core.Notes;

public class NotesDataClass {
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
  public Notes getNotes() {
      // TODO implement the method
      Notes notes = new Notes(); // from restserver
      return notes;
  }

  /**
   * http://localhost:8080/notes/{index}
   * GET
   */
  public Note getNote(int index) {
    // TODO implement the method
    Note note = new Note(); // from restserver
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

package notiskblokk.restapi;

import notisblokk.core.Note;
import notisblokk.core.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NoteController {

  @Autowired
  private NoteService service = new NoteService();

  public NoteService getNoteService() {
    return this.service;
  }

  @GetMapping(produces = "application/json")
  public Notes getNotes() {
    return service.getAllNotes();
  }

  /**
   * Appends a note to the list of notes.
   *
   * @param note
   * @return
   */
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Note> addNote(@RequestBody Note note) {
    if (service.addNote(note)) {
      int index = service.getAllNotes().getNumNotes() - 1;
      return ResponseEntity.ok(service.getNote(index));
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * Fetches the note at the given index
   *
   * @param index
   * @return
   */
  @GetMapping(produces = "application/json")
  @RequestMapping("/{index}")
  public ResponseEntity<Note> getNote(@PathVariable int index) {
    Note noteFound = service.getNote(index);
    if (noteFound != null) {
      return ResponseEntity.ok(noteFound);
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Replaces the param note with the current note at the given index
   *
   * @param index
   * @param note
   * @return
   */
  @PostMapping(value = "/{index}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Note> setNote(@PathVariable int index, @RequestBody Note note) {
    Note noteAtIndex = service.getNote(index);
    if (noteAtIndex != null) {
      return ResponseEntity.ok(service.replaceNote(index, note));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping(value = "/{index}")
  public ResponseEntity<Note> deleteNote(@PathVariable int index) {
    if (service.removeNote(index)) {
      ResponseEntity.ok(service.getNote(index));
    }
    return ResponseEntity.badRequest().build();
  }

}

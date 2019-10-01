package notiskblokk.restapi;

import java.net.URI;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/notes")
public class NoteController {

  @Autowired
  private NoteService service;

  @GetMapping(produces = "application/json")
  public Notes getNotes() {
    return service.getAllNotes();
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Note> addNote(@RequestBody Note note) {
    System.out.println(note);

    service.addNote(note);
    int index = service.getAllNotes().getNumNotes();

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{index}")
        .buildAndExpand(index)
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping(produces = "application/json")
  @RequestMapping("/{index}")
  public ResponseEntity<Note> getNote(@PathVariable int index) {
    Note noteFound = service.getNote(index);
    if (noteFound != null) {
      return ResponseEntity.ok(noteFound);
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping("/{index}")
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Note> setNote(@PathVariable int index, @RequestBody Note note) {
    Note noteAtIndex = service.getNote(index);
    if (noteAtIndex != null){
      return ResponseEntity.ok(service.replaceNote(index, note));
    }
    return ResponseEntity.notFound().build();
  }

}

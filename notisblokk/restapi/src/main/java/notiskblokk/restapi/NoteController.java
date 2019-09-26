package notiskblokk.restapi;

import java.net.URI;
import notisblokk.core.Note;
import notisblokk.core.Notes;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<Object> addNote(@RequestBody Note note) {
    System.out.println(note);

    service.addNote(note);
    int index = service.getAllNotes().getNumNotes();

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{index}")
        .buildAndExpand(index)
        .toUri();

    return ResponseEntity.created(location).build();
  }
}

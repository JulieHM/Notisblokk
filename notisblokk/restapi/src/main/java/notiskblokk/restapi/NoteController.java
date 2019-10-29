package notiskblokk.restapi;

import java.util.List;
import notisblokk.core.Note;
import notisblokk.core.Category;
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
  public ResponseEntity<List<Category>> getCategories() {
    return ResponseEntity.ok(service.getAllCategories());
  }

  /**
   * Appends a note to the list of notes.
   */
  @PostMapping(value = "/category/{categoryIndex}/notes", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Note> addNote(@PathVariable int categoryIndex, @RequestBody Note note) {
    Category category = service.getCategory(categoryIndex);
    if (category.addNote(note)) {
      int index = category.getNumNotes() - 1;
      return ResponseEntity.ok(service.getNote(index));
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * Appends a category at the end of the list of categories
   *
   * @param category
   * @return
   */
  @PostMapping(value = "/category", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Category> addCategory(@RequestBody Category category) {
    if (service.addCategory(category)) {
      int index = service.getNumCategories() - 1;
      return ResponseEntity.ok(service.getCategory(index));
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * Fetches the note at the given index
   */
  @GetMapping(produces = "application/json")
  @RequestMapping("/category/{categoryIndex}/notes/{noteIndex}")
  public ResponseEntity<Note> getNote(@PathVariable int categoryIndex,
      @PathVariable int noteIndex) {
    Note noteFound = service.getCategory(categoryIndex).getNote(noteIndex);
    if (noteFound != null) {
      return ResponseEntity.ok(noteFound);
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Gets the category at the given index
   *
   * @param index
   * @return
   */
  @GetMapping(produces = "application/json")
  @RequestMapping("/category/{index}")
  public ResponseEntity<Category> getCategory(@PathVariable int index) {
    Category categoryFound = service.getCategory(index);
    if (categoryFound != null) {
      return ResponseEntity.ok(categoryFound);
    }
    return ResponseEntity.notFound().build();
  }


  /**
   * Replaces the param note with the current note at the given index
   */
  /*
  @PostMapping(value = "/{index}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Note> setNote(@PathVariable int index, @RequestBody Note note) {
    Note noteAtIndex = service.getNote(index);
    if (noteAtIndex != null) {
      return ResponseEntity.ok(service.replaceNote(index, note));
    }
    return ResponseEntity.notFound().build();
  }
   */

  @PostMapping(value = "/category/{index}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Category> renameCategory(@PathVariable int index,
      @RequestBody String name) {
    Category categoryAtIndex = service.getCategory(index);
    if (categoryAtIndex != null) {
      categoryAtIndex.setName(name);
      return ResponseEntity.ok(categoryAtIndex);
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Deletes the note at the given index if found.
   */
  /*
  @DeleteMapping(value = "/{index}")
  public ResponseEntity<Note> deleteNote(@PathVariable int index) {
    if (service.removeNote(index)) {
      ResponseEntity.ok(service.getNote(index));
    }
    return ResponseEntity.badRequest().build();
  }
   */

  @DeleteMapping(value = "/category/{index}")
  public ResponseEntity<Category> deleteCategory(@PathVariable int index) {
    if (service.removeCategory(index)) {
      ResponseEntity.ok(service.getAllCategories());
    }
    return ResponseEntity.badRequest().build();
  }

}

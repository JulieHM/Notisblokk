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
    if (service.addNote(categoryIndex, note)) {
      int index = service.getCategory(categoryIndex).getNumNotes() - 1;
      return ResponseEntity.ok(service.getNote(categoryIndex, index));
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * Appends a category at the end of the list of categories
   *
   * @param category to add
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

  @GetMapping(produces = "application/json")
  @RequestMapping("/category/{categoryIndex}/notes")
  public ResponseEntity<List<Note>> getNotes(@PathVariable int categoryIndex) {
    try {
      return ResponseEntity.ok(service.getCategory(categoryIndex).getNotes());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
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
  @PostMapping(value = "/category/{catIndex}/notes/{index}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Note> setNote(@PathVariable int catIndex, @PathVariable int index,
      @RequestBody Note note) {
    if (service.replaceNote(catIndex, index, note)) {
      return ResponseEntity.ok(service.getNote(catIndex, index));
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Renames the category at the given index
   *
   * @param index of the category
   * @param name  to be replaced with
   * @return
   */
  @PostMapping(value = "/category/{index}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Category> renameCategory(@PathVariable int index,
      @RequestBody String name) {
    if (service.renameCategory(index, name)) {
      return ResponseEntity.ok(service.getCategory(index));
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Deletes the note at the given index if found.
   */
  @DeleteMapping(value = "/category/{catIndex}/notes/{index}")
  public ResponseEntity<Note> deleteNote(@PathVariable int catIndex, @PathVariable int index) {
    try {
      service.getCategory(catIndex).removeNote(index);
      return ResponseEntity.ok(service.getNote(catIndex, index));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Deletes the category at the given index
   *
   * @param index
   * @return
   */
  @DeleteMapping(value = "/category/{index}")
  public ResponseEntity<Category> deleteCategory(@PathVariable int index) {
    if (service.removeCategory(index)) {
      ResponseEntity.ok(service.getAllCategories());
    }
    return ResponseEntity.badRequest().build();
  }

}

package notiskblokk.restapi;

import java.util.List;
import notisblokk.core.Category;
import notisblokk.core.Note;
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
@RequestMapping("/categories")
public class NoteController {

  @Autowired
  private NoteService service = new NoteService();

  /**
   * Gets the note service.
   */
  public NoteService getNoteService() {
    return this.service;
  }

  /**
   * Gets the categories.
   */
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<Category>> getCategories() {
    return ResponseEntity.ok(service.getAllCategories());
  }

  /**
   * Gets the category at the given index.
   * @param index of the category.
   * @return a response entity with status.
   */
  @GetMapping(produces = "application/json")
  @RequestMapping("/{index}")
  public ResponseEntity<Category> getCategory(@PathVariable int index) {
    Category categoryFound = service.getCategory(index);
    if (categoryFound != null) {
      return ResponseEntity.ok(categoryFound);
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Appends a category at the end of the list of categories.
   * @param category to add.
   * @return a response entity with status.
   */
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Category> addCategory(@RequestBody Category category) {
    if (service.addCategory(category)) {
      int index = service.getNumCategories() - 1;
      return ResponseEntity.ok(service.getCategory(index));
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * Deletes the category at the given index.
   * @param index of the category.
   * @return a response entity with status.
   */
  @DeleteMapping(value = "/{index}")
  public ResponseEntity<Category> deleteCategory(@PathVariable int index) {
    if (service.removeCategory(index)) {
      ResponseEntity.ok(service.getAllCategories());
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * Appends a note to a category.
   */
  @PostMapping(value = "/{categoryIndex}/notes", consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<Note> addNote(@PathVariable int categoryIndex, @RequestBody Note note) {
    if (service.addNote(categoryIndex, note)) {
      int index = service.getCategory(categoryIndex).getNumNotes() - 1;
      return ResponseEntity.ok(service.getNote(categoryIndex, index));
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * Fetches the notes from a category.
   * @param categoryIndex in the notebook.
   * @return a response entity with status.
   */
  @GetMapping(produces = "application/json")
  @RequestMapping("/{categoryIndex}/notes")
  public ResponseEntity<List<Note>> getNotes(@PathVariable int categoryIndex) {
    try {
      return ResponseEntity.ok(service.getCategory(categoryIndex).getNotes());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Replaces the category with a new category. Is called renameCategory,
   * as that's the only purpose it serves as of now.
   * @param category with the new name.
   * @param categoryIndex of the category to be replaced.
   * @return a response entity with status.
   */
  @PostMapping(value = "/{categoryIndex}", consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<Category> renameCategory(@RequestBody Category category,
      @PathVariable int categoryIndex) {
    if (service.renameCategory(categoryIndex, category)) {
      int index = service.getNumCategories() - 1;
      return ResponseEntity.ok(service.getCategory(index));
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * Gets a note from a category.
   * @param categoryIndex index of the category.
   * @param noteIndex index of the note.
   * @return a response entity with status.
   */
  @GetMapping(produces = "application/json")
  @RequestMapping("/{categoryIndex}/notes/{noteIndex}")
  public ResponseEntity<Note> getNote(@PathVariable int categoryIndex,
      @PathVariable int noteIndex) {
    Note noteFound = service.getCategory(categoryIndex).getNote(noteIndex);
    if (noteFound != null) {
      return ResponseEntity.ok(noteFound);
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Replaces a note with new note.
   * @param catIndex index of category in which the note is.
   * @param index of the note to be replaced.
   * @param note to replace with.
   * @return a response entity with status.
   */
  @PostMapping(value = "/{catIndex}/notes/{index}", consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<Note> setNote(@PathVariable int catIndex, @PathVariable int index,
      @RequestBody Note note) {
    if (service.replaceNote(catIndex, index, note)) {
      return ResponseEntity.ok(service.getNote(catIndex, index));
    }
    return ResponseEntity.notFound().build();
  }


  /**
   * Deletes the note at the given index if found.
   */
  @DeleteMapping(value = "/{catIndex}/notes/{index}")
  public ResponseEntity<Note> deleteNote(@PathVariable int catIndex, @PathVariable int index) {
    try {
      service.getCategory(catIndex).removeNote(index);
      return ResponseEntity.ok(service.getNote(catIndex, index));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}

package notiskblokk.restapi;

import notisblokk.core.Note;

class RestInvalidNoteFormatException extends RuntimeException {

  RestInvalidNoteFormatException(Note note) {
    super(String.format("Could not add note with format:\n%s", note));
  }
  
}

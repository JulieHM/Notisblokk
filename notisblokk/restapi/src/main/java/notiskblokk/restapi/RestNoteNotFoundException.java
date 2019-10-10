package notiskblokk.restapi;

public class RestNoteNotFoundException extends RuntimeException {

  /**
   * Class called and constructed if a note at a given index is not found.
   *
   * @param index
   */
  RestNoteNotFoundException(int index){
    super(String.format("Note with index %s not found.", index));
  }

}

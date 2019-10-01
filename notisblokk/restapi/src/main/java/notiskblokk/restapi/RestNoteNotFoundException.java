package notiskblokk.restapi;


public class RestNoteNotFoundException extends RuntimeException {

  public RestNoteNotFoundException(int index){
    super(String.format("Note with index %s not found.", index));
  }

}

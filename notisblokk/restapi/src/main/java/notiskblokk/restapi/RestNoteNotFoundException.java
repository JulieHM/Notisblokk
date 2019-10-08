package notiskblokk.restapi;


class RestNoteNotFoundException extends RuntimeException {

  RestNoteNotFoundException(int index){
    super(String.format("Note with index %s not found.", index));
  }

}

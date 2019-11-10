package notiskblokk.restapi;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Custom error response created if RestNoteNotFoundException is thrown.
   *
   * @param ex exception that is thrown
   * @param request webrequest
   */
  @ExceptionHandler(RestNoteNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex,
      WebRequest request) {
    CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        ex.getMessage());
    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
  }

}

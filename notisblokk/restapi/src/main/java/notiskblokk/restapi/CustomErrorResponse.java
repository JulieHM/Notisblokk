package notiskblokk.restapi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomErrorResponse {

  private String timestamp;
  private int status;
  private String error;

  /**
   * Custom error response which includes the datetime, HTTP-status message and an error string.
   *
   * @param timestamp for when error occurred.
   * @param status message
   * @param error string
   */
  public CustomErrorResponse(LocalDateTime timestamp, int status, String error) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    this.timestamp = timestamp.format(formatter);
    this.status = status;
    this.error = error;
  }

  /**
   * Gets timestamp.
   */
  public String getTimestamp() {
    return timestamp;
  }

  /**
   * Gets status.
   */
  public int getStatus() {
    return status;
  }

  /**
   * Gets errors.
   */
  public String getError() {
    return error;
  }

}

package notiskblokk.restapi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomErrorResponse {

  private String timestamp;
  private int status;
  private String error;

  public CustomErrorResponse(LocalDateTime timestamp, int status, String error) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    this.timestamp = timestamp.format(formatter);
    this.status = status;
    this.error = error;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

}

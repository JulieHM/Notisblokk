package notisblokk.model;

import java.time.LocalDateTime;

public class Note {

  private String title;
  private String message;
  private LocalDateTime lastEditedDate;
  private final LocalDateTime createdDate;

  public Note(String title, String message, LocalDateTime createdDate,
      LocalDateTime lastEditedDate) {
    this.title = title;
    this.message = message;
    this.createdDate = createdDate;
    this.lastEditedDate = lastEditedDate;
  }

  public Note(String title, String message) {
    this(title, message, LocalDateTime.now(), LocalDateTime.now());
  }

  public String getTitle() {
    return title;
  }

  public String getMessage() {
    return message;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setLastEditedDate() {
    this.lastEditedDate = LocalDateTime.now();
  }

  public LocalDateTime getLastEditedDate() {
    return lastEditedDate;
  }
}

package notisblokk.core;

import java.time.LocalDateTime;

public class Note implements Comparable<Note> {

  private String title;
  private String message;
  private LocalDateTime lastEditedDate;
  private final LocalDateTime createdDate;

  /**
   * Creates a new Note object with the given parameters.
   * @param title The title of the Note.
   * @param message The message of the Note.
   * @param createdDate the creation date of the Note.
   * @param lastEditedDate the last edited date of the Note.
   */
  public Note(String title, String message, LocalDateTime createdDate,
      LocalDateTime lastEditedDate) {
    this.title = title;
    this.message = message;
    this.createdDate = createdDate;
    this.lastEditedDate = lastEditedDate;
  }

  /**
   * Creates a new Note object with the given parameters. This is mainly used for
   * new note objects as the creation and last edited date will be auto generated.
   * @param title The title of the Note.
   * @param message The message of the Note.
   */
  public Note(String title, String message) {
    this(title, message, LocalDateTime.now(), LocalDateTime.now());
  }

  /**
   * Gets the title of this Note.
   * @return this Note's title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the message of this Note.
   * @return this Note's message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets the creation date of this Note.
   * @return this Note's creation date.
   */
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  /**
   * Changes the title of this Note.
   * @param title This Note's new title.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Changes the message of this Note.
   * @param message This note's new Message.
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Changes the last edited date to current date/time of this Note.
   */
  public void setLastEditedDate() {
    this.lastEditedDate = LocalDateTime.now();
  }

  /**
   * Gets the last edited date of this Note.
   * @return this Note's last edited date.
   */
  public LocalDateTime getLastEditedDate() {
    return lastEditedDate;
  }

  @Override
  public String toString() {
    return "Note{"
        + "title='" + title
        + "', message='" + message
        + "', lastEditedDate=" + lastEditedDate
        + ", createdDate=" + createdDate
        + '}';
  }

  @Override
  public int compareTo(Note note) {
    return this.getLastEditedDate().compareTo(note.getLastEditedDate());
  }
}

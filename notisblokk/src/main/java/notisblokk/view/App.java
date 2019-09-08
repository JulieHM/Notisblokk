package notisblokk.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/notisblokk/gui/teste.fxml"));

    Scene scene = new Scene(root);

    stage.setTitle("Notisblokk");
    stage.setScene(scene);
    stage.show();

    stage.setResizable(false);
  }

  /**
   * The main entry point of the program. This starts up the JavaFX application.
   */
  public static void main(String[] args) {
    launch(args);
  }
}

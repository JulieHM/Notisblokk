package notisblokk.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import notisblokk.core.Category;

/**
 * @author utente
 */
public class TabSetText {

  private FxAppController controller;

  public TabSetText(FxAppController controller) {
    this.controller = controller;
  }

  public TabWithCategory createEditableTab(String text, Category category) {
    final Label label = new Label(text);
    final TabWithCategory tab = new TabWithCategory(category);
    tab.setGraphic(label);
    final TextField textField = new TextField();
    label.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          textField.setText(label.getText());
          tab.setGraphic(textField);
          textField.selectAll();
          textField.requestFocus();
        }
      }
    });

    textField.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        label.setText(textField.getText());
        tab.setGraphic(label);
        category.setName(textField.getText());
      }
    });

    textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable,
          Boolean oldValue, Boolean newValue) {
        if (!newValue) {
          label.setText(textField.getText());
          tab.setGraphic(label);
        }
      }
    });
    return tab;
  }

}

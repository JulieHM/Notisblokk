package notisblokk.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import notisblokk.core.Category;

/**
 * Class to with methods for setting editable tabs.
 * @author utente
 */
public class TabSetText {

  private FxAppController controller;

  /**
   * Constructor which sets FxAppController as this.controller.
   */
  public TabSetText(FxAppController controller) {
    this.controller = controller;
  }

  /**
   * Method for creating editable tabs.
   *
   * @param text to be set as title
   * @param category tab to set editable
   */
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
        controller.setActiveCategory(category);
      }
    });

    textField.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        label.setText(textField.getText());
        tab.setGraphic(label);
        category.setName(textField.getText());
        controller.renameCategory(category, textField.getText());
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

    tab.setOnCloseRequest(new EventHandler<Event>() {
      @Override
      public void handle(Event arg0) {
        controller.deleteCategory();
      }
    });

    return tab;
  }
}

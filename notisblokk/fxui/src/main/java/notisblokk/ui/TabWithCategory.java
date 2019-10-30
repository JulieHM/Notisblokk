package notisblokk.ui;

import javafx.scene.control.Tab;
import notisblokk.core.Category;

public class TabWithCategory extends Tab {

  private Category category;

  public TabWithCategory(Category category){
    super();
    this.category = category;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}

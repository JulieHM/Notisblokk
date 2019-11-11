package notisblokk.ui;

import javafx.scene.control.Tab;
import notisblokk.core.Category;

public class TabWithCategory extends Tab {

  private Category category;

  /**
   * Constructor which creates a tab with a category.
   *
   * @param category to be set to tab
   */
  public TabWithCategory(Category category) {
    super();
    this.category = category;
  }

  /**
   * Gets the category of this tab.
   * @return this tab's category
   */
  public Category getCategory() {
    return category;
  }

  /**
   * Sets the category to this tab.
   * @param category to be set to this tab
   */
  public void setCategory(Category category) {
    this.category = category;
  }
}

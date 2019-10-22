package notisblokk.core;

import java.util.ArrayList;
import java.util.List;

public class Notebook {

  private List<Category> categories;
  private Category activeCategory;

  /**
   * Default constructor
   *
   * @param categories
   */
  public Notebook(List<Category> categories) {
    this.categories = categories;
  }

  /**
   * Adds a new category to the notebook
   *
   * @param name of the category
   */
  public void addCategory(String name) {
    categories.add(new Category(name));
  }

  /**
   * Gets all the categories in the Notebook
   * @return all categories
   */
  public List<Category> getCategories() {
    return categories;
  }

  /**
   * Sets the active category
   * @param activeCategory active category
   */
  public void setActiveCategory(Category activeCategory) {
    this.activeCategory = activeCategory;
  }

  /**
   * Gets the active category (active tab)
   * @return the active tab
   */
  public Category getActiveCategory() {
    return activeCategory;
  }
}

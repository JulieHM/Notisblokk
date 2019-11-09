package notisblokk.core;

import java.util.ArrayList;
import java.util.List;

public class Notebook {

  private List<Category> categories = new ArrayList<>();

  /**
   * Empty constructor.
   */
  public Notebook() {
  }

  /**
   * Default constructor.
   *
   * @param categories to initialize notebook with.
   */
  public Notebook(List<Category> categories) {
    this.categories = categories;
  }

  /**
   * Add a category to the notebook.
   *
   * @param category to be added
   */
  public void addCategory(Category category) {
    this.categories.add(category);
  }

  /**
   * Gets all the categories in the Notebook.
   *
   * @return all categories
   */
  public List<Category> getCategories() {
    return categories;
  }

  /**
   * Gets a specific category from the notebook.
   *
   * @param index of the category in the list
   * @return the category at the index
   */
  public Category getCategory(int index) {
    return categories.get(index);
  }

  /**
   * Removes the category at the index of the list.
   *
   * @param index of the category
   */
  public void removeCategory(int index) {
    categories.remove(index);
  }
}

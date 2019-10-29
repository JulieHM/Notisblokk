package notisblokk.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class Notebook {

  private List<Category> categories = new ArrayList<>();
  private Category activeCategory;

  public Notebook() {
  }

  /**
   * Default constructor
   *
   * @param categories
   */
  public Notebook(List<Category> categories) {
    this.categories = categories;
  }

  public void addCategory(Category category) {
    this.categories.add(category);
  }

  public void addCategory(List<Category> categories){
    this.categories.addAll(categories);
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
   *
   * @return all categories
   */
  public List<Category> getCategories() {
    return categories;
  }

  /**
   * Sets the active category
   *
   * @param activeCategory active category
   */
  public void setActiveCategory(Category activeCategory) {
    this.activeCategory = activeCategory;
  }

  /**
   * Gets the active category (active tab)
   *
   * @return the active tab
   */
  public Category getActiveCategory() {
    if (activeCategory == null) {
      return categories.get(0);
    }
    return activeCategory;
  }

  public Category getCategory(int index){
    return categories.get(index);
  }

  public boolean removeCategory(Category category) {
    try {
      categories.remove(category);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public boolean removeCategory(int index) {
    try {
      categories.remove(index);
      return true;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }
}

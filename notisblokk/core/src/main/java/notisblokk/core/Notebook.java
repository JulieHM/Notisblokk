package notisblokk.core;

import java.util.ArrayList;
import java.util.List;

public class Notebook {

  List<Category> categories = new ArrayList<>();

  /**
   * Default constructor
   * @param categories
   */
  public Notebook(List<Category> categories) {
    this.categories = categories;
  }

  /**
   * Adds a new category to the notebook
   * @param name of the category
   */
  public void addCategory(String name){
    categories.add(new Category(name));
  }

  /**
   * Get all categories of the Notebook
   */
  public List<Category> getCategories(){
    return categories;
  }

}

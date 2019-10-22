package notisblokk.core;

import java.util.ArrayList;
import java.util.List;

public class Notebook {

  List<Category> categories = new ArrayList<>();

  public Notebook(){
    // hehe
  }

  /**
   * Default constructor
   * @param categories
   */
  public Notebook(List<Category> categories) {
    this.categories = categories;
  }

  public void addCategory(Category category){
    categories.add(category);
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

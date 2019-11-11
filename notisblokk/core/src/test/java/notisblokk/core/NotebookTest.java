package notisblokk.core;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NotebookTest {

  private Notebook notebook;

  /**
   * Inits notebook before the tests.
   */
  @Before
  public void init() {
    notebook = new Notebook();
  }

  /**
   * Tests the constructor and getCategory function.
   */
  @Test
  public void testConstructor() {
    Category cat1 = new Category("cat 1");
    Category cat2 = new Category("cat 2");
    List<Category> catList = new ArrayList<>();
    catList.add(cat1);
    catList.add(cat2);
    notebook = new Notebook(catList);
    Assert.assertEquals(2, notebook.getCategories().size());
    Assert.assertEquals(cat1, notebook.getCategory(0));
    Assert.assertEquals(cat2, notebook.getCategory(1));
  }

  /**
   * Tests both addCategory and getCategory.
   */
  @Test
  public void testAddAndGetCategory() {
    Category cat1 = new Category("cat 1");
    notebook.addCategory(cat1);
    Assert.assertEquals(cat1, notebook.getCategory(0));
  }

  /**
   * Tests the fetching of all categories.
   */
  @Test
  public void testGetCategories() {
    Category cat1 = new Category("cat 1");
    Category cat2 = new Category("cat 2");
    List<Category> catList = new ArrayList<>();
    catList.add(cat1);
    catList.add(cat2);
    notebook = new Notebook(catList);
    Assert.assertEquals(catList, notebook.getCategories());
  }

  /**
   * Tests the removal of categories inside a notebook.
   */
  @Test
  public void testRemoveCategory(){
    Category cat1 = new Category("cat 1");
    notebook.addCategory(cat1);
    Assert.assertEquals(cat1, notebook.getCategory(0));
    notebook.removeCategory(0);
    Assert.assertEquals(0, notebook.getCategories().size());
  }

}

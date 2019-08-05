package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.Category;
import net.caidingke.domain.query.QCategory;

public class CategoryFinder extends Finder<Long,Category> {

  /**
   * Construct using the default EbeanServer.
   */
  public CategoryFinder() {
    super(Category.class);
  }

  /**
   * Start a new typed query.
   */
  public QCategory where() {
    return new QCategory(db());
  }
}

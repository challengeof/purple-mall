package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.CategoryBrandXref;
import net.caidingke.domain.query.QCategoryBrandXref;

public class CategoryBrandXrefFinder extends Finder<Long,CategoryBrandXref> {

  /**
   * Construct using the default EbeanServer.
   */
  public CategoryBrandXrefFinder() {
    super(CategoryBrandXref.class);
  }

  /**
   * Start a new typed query.
   */
  public QCategoryBrandXref where() {
    return new QCategoryBrandXref(db());
  }
}

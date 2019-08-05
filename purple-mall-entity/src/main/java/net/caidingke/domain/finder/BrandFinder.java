package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.Brand;
import net.caidingke.domain.query.QBrand;

public class BrandFinder extends Finder<Long,Brand> {

  /**
   * Construct using the default EbeanServer.
   */
  public BrandFinder() {
    super(Brand.class);
  }

  /**
   * Start a new typed query.
   */
  public QBrand where() {
    return new QBrand(db());
  }
}

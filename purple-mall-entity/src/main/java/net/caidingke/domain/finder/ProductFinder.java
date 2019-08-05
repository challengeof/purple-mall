package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.Product;
import net.caidingke.domain.query.QProduct;

public class ProductFinder extends Finder<Long,Product> {

  /**
   * Construct using the default EbeanServer.
   */
  public ProductFinder() {
    super(Product.class);
  }

  /**
   * Start a new typed query.
   */
  public QProduct where() {
    return new QProduct(db());
  }
}

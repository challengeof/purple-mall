package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.ProductBasicSpec;
import net.caidingke.domain.query.QProductBasicSpec;

public class ProductBasicSpecFinder extends Finder<Long,ProductBasicSpec> {

  /**
   * Construct using the default EbeanServer.
   */
  public ProductBasicSpecFinder() {
    super(ProductBasicSpec.class);
  }

  /**
   * Start a new typed query.
   */
  public QProductBasicSpec where() {
    return new QProductBasicSpec(db());
  }
}

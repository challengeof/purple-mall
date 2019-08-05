package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.Sku;
import net.caidingke.domain.query.QSku;

public class SkuFinder extends Finder<Long,Sku> {

  /**
   * Construct using the default EbeanServer.
   */
  public SkuFinder() {
    super(Sku.class);
  }

  /**
   * Start a new typed query.
   */
  public QSku where() {
    return new QSku(db());
  }
}

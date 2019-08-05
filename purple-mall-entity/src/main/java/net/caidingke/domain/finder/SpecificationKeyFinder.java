package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.SpecificationKey;
import net.caidingke.domain.query.QSpecificationKey;

public class SpecificationKeyFinder extends Finder<Long,SpecificationKey> {

  /**
   * Construct using the default EbeanServer.
   */
  public SpecificationKeyFinder() {
    super(SpecificationKey.class);
  }

  /**
   * Start a new typed query.
   */
  public QSpecificationKey where() {
    return new QSpecificationKey(db());
  }
}

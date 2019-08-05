package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.SpecificationValue;
import net.caidingke.domain.query.QSpecificationValue;

public class SpecificationValueFinder extends Finder<Long,SpecificationValue> {

  /**
   * Construct using the default EbeanServer.
   */
  public SpecificationValueFinder() {
    super(SpecificationValue.class);
  }

  /**
   * Start a new typed query.
   */
  public QSpecificationValue where() {
    return new QSpecificationValue(db());
  }
}

package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.Ebean;

public class EbeanFinder extends Finder<Long,Ebean> {

  /**
   * Construct using the default EbeanServer.
   */
  public EbeanFinder() {
    super(Ebean.class);
  }

}

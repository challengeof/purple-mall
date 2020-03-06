package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.CGroup;

public class CGroupFinder extends Finder<Long,CGroup> {

  /**
   * Construct using the default EbeanServer.
   */
  public CGroupFinder() {
    super(CGroup.class);
  }

}

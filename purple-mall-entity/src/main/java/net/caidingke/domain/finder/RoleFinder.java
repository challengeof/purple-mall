package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.Role;
import net.caidingke.domain.query.QRole;

public class RoleFinder extends Finder<Long,Role> {

  /**
   * Construct using the default EbeanServer.
   */
  public RoleFinder() {
    super(Role.class);
  }

  /**
   * Start a new typed query.
   */
  public QRole where() {
    return new QRole(db());
  }
}

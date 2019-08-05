package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.UserRole;
import net.caidingke.domain.query.QUserRole;

public class UserRoleFinder extends Finder<Long,UserRole> {

  /**
   * Construct using the default EbeanServer.
   */
  public UserRoleFinder() {
    super(UserRole.class);
  }

  /**
   * Start a new typed query.
   */
  public QUserRole where() {
    return new QUserRole(db());
  }
}

package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.Permission;
import net.caidingke.domain.query.QPermission;

public class PermissionFinder extends Finder<Long,Permission> {

  /**
   * Construct using the default EbeanServer.
   */
  public PermissionFinder() {
    super(Permission.class);
  }

  /**
   * Start a new typed query.
   */
  public QPermission where() {
    return new QPermission(db());
  }
}

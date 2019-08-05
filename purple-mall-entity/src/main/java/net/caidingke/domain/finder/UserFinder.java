package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.User;
import net.caidingke.domain.query.QUser;

public class UserFinder extends Finder<Long,User> {

  /**
   * Construct using the default EbeanServer.
   */
  public UserFinder() {
    super(User.class);
  }

  /**
   * Start a new typed query.
   */
  public QUser where() {
    return new QUser(db());
  }
}

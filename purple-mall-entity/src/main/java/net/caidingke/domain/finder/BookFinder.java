package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.Book;
import net.caidingke.domain.query.QBook;

public class BookFinder extends Finder<Long,Book> {

  /**
   * Construct using the default EbeanServer.
   */
  public BookFinder() {
    super(Book.class);
  }

  /**
   * Start a new typed query.
   */
  public QBook where() {
    return new QBook(db());
  }

}

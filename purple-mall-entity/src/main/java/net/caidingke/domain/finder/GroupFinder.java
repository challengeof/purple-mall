package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.CGroup;
import net.caidingke.domain.query.QCGroup;

public class GroupFinder extends Finder<Long, CGroup> {

    /**
     * Construct using the default EbeanServer.
     */
    public GroupFinder() {
        super(CGroup.class);
    }

    /**
     * Start a new typed query.
     */
    public QCGroup where() {
        return new QCGroup(db());
    }
}

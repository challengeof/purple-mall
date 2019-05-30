package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.RolePermission;
import net.caidingke.domain.query.QRolePermission;

public class RolePermissionFinder extends Finder<Long, RolePermission> {

    /**
     * Construct using the default EbeanServer.
     */
    public RolePermissionFinder() {
        super(RolePermission.class);
    }

    /**
     * Start a new typed query.
     */
    public QRolePermission where() {
        return new QRolePermission();
    }
}

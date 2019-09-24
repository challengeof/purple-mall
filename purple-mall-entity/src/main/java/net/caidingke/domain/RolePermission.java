package net.caidingke.domain;

import io.ebean.annotation.Index;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.RolePermissionFinder;

import javax.persistence.Entity;

/**
 * @author bowen
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Index(columnNames = {"role_id", "permission_id"})
public class RolePermission extends BaseModel {

    public static final RolePermissionFinder find = new RolePermissionFinder();
    private static final long serialVersionUID = -5056392712622093861L;
    private Long roleId;
    private Long permissionId;
}

package net.caidingke.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.PermissionFinder;

import javax.persistence.Entity;

/**
 * @author bowen
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseModel {

    public static final PermissionFinder find = new PermissionFinder();
    private static final long serialVersionUID = 2726305098180580139L;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 状态 {@link net.caidingke.domain.enums.DisableEnabledStatus}
     */
    private Integer status;

    /**
     * 具体权限
     */
    private String permission;

    /**
     * 显示顺序
     */
    private Integer displayOrder;
}

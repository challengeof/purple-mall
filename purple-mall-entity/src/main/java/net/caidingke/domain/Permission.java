package net.caidingke.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.PermissionFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
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

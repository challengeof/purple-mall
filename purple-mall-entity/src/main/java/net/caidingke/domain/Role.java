package net.caidingke.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.RoleFinder;

import javax.persistence.Entity;

/**
 * @author bowen
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseModel {

    public static final RoleFinder find = new RoleFinder();
    private static final long serialVersionUID = 440298941930215684L;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 显示名字
     */
    private String displayName;
    /**
     * 状态 {@link net.caidingke.domain.enums.DisableEnabledStatus}
     */
    private Integer status;

    /**
     * 显示顺序
     */
    private Integer displayOrder;
}

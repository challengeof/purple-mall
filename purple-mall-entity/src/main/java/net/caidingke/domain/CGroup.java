package net.caidingke.domain;

import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.CGroupFinder;

import javax.persistence.Entity;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
public class CGroup extends BaseModel {

    public static final CGroupFinder find = new CGroupFinder();

    private static final long serialVersionUID = -4981047452848552895L;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 组名称
     */
    private String groupName;

}

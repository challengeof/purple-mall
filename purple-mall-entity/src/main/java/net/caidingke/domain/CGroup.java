package net.caidingke.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.GroupFinder;

import javax.persistence.Entity;

/**
 * @author bowen
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CGroup extends BaseModel {

    public static final GroupFinder find = new GroupFinder();

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

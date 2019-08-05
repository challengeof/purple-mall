package net.caidingke.domain;

import io.ebean.annotation.Index;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.SpecificationKeyFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
@Index(columnNames = {"status", "category_id", "group_id"})
public class SpecificationKey extends BaseModel {

    public static final SpecificationKeyFinder find = new SpecificationKeyFinder();

    private static final long serialVersionUID = 1919769762775269457L;
    /**
     * 属性名称
     */
    private String specName;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 是否销售属性
     */
    private boolean saleProps;
    /**
     * 组id
     */
    private Long groupId;
    /**
     * 是否搜索属性
     */
    private boolean searchProps;
    /**
     * 排序值
     */
    private Integer sort;
}

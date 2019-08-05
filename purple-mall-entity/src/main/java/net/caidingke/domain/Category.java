package net.caidingke.domain;

import io.ebean.annotation.Index;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.CategoryFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
@Index(columnNames = {"status", "parent_id", "name"})
public class Category extends BaseModel {

    public static final CategoryFinder find = new CategoryFinder();

    private static final long serialVersionUID = -920094880294834143L;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类父id
     */
    private Long parentId;
    /**
     * 级别
     */
    private Integer level;
    /**
     * icon
     */
    private String icon;
    /**
     * 导航栏是否显示
     */
    private boolean navigationBarShow;
    /**
     * 状态 {@link net.caidingke.domain.enums.DisableEnabledStatus}
     */
    private Integer status;
    /**
     * 显示顺序
     */
    private Integer displayOrder;
    /**
     * 单位
     */
    private String unit;
    /**
     * 描述
     */
    private String description;

}

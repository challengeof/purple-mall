package net.caidingke.domain;

import io.ebean.annotation.Index;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.BrandFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
@Index(columnNames = {"status", "first_letter", "name"})
public class Brand extends BaseModel {

    public static final BrandFinder find = new BrandFinder();

    private static final long serialVersionUID = -6367675311638922284L;
    /**
     * 品牌名称
     */
    private String name;
    /**
     * 品牌名称首字母
     */
    private String firstLetter;
    /**
     * 品牌故事
     */
    private String brandStory;
    /**
     * logo
     */
    private String logo;
    /**
     * 英文名称
     */
    private String englishName;
    /**
     * 品牌区域
     */
    private String area;
    /**
     * 状态 {@link net.caidingke.domain.enums.DisableEnabledStatus}
     */
    private Integer status;
    /**
     * 显示顺序
     */
    private Integer displayOrder;
    /**
     * 描述
     */
    private String description;
}

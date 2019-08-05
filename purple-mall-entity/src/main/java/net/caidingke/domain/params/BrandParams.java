package net.caidingke.domain.params;

import lombok.Data;

/**
 * @author bowen
 */
@Data
public class BrandParams {

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

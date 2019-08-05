package net.caidingke.domain;

import io.ebean.annotation.SoftDelete;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.ProductFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
public class Product extends BaseModel {

    public static final ProductFinder find = new ProductFinder();

    private static final long serialVersionUID = 7237523284429985700L;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 品牌名称 冗余
     */
    private String brandName;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 分类名称 冗余
     */
    private String categoryName;
    /**
     * 商品图片多图英文分号分割(;)
     */
    @Column(columnDefinition = "text")
    private String picture;
    /**
     * 商品编码
     */
    private String code;
    /**
     * 上下架状态 {@link net.caidingke.domain.enums.ProductStatus}
     */
    private Integer publishStatus;
    /**
     * 推荐状态 {@link net.caidingke.domain.enums.ProductStatus}
     */
    private Integer recommendStatus;
    /**
     * 审核状态 {@link net.caidingke.domain.enums.ProductStatus}
     */
    private Integer verifyStatus;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 销量
     */
    private Integer saleCount;
    /**
     * 描述
     */
    private String description;
    /**
     * 数量
     */
    private long quantity;
    /**
     * 标语
     */
    private String slogan;
    /**
     * 删除状态
     */
    @SoftDelete
    private boolean deleted;
}

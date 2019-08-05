package net.caidingke.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.ProductBasicSpecFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
public class ProductBasicSpec extends BaseModel {

    public static final ProductBasicSpecFinder find = new ProductBasicSpecFinder();

    private static final long serialVersionUID = -271288878515955641L;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 属性名id
     */
    private Long specKeyId;
    /**
     * 属性值id
     */
    private Long specValueId;
    /**
     * 是否是全局属性 true 全局属性 false sku特有属性
     */
    private boolean global;
    /**
     * sku id 如果global 为true 此值不能为空
     */
    private Long skuId;
}

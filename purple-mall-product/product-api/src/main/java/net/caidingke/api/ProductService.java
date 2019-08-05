package net.caidingke.api;

import net.caidingke.domain.Product;

/**
 * @author bowen
 */
public interface ProductService {

    /**
     * 根据商品id查询商品
     *
     * @param id 商品id
     * @return 商品实体
     */
    Product findById(Long id);

    /**
     * 创建商品
     *
     * @param product 商品实体
     */
    void save(Product product);
}

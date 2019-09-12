package net.caidingke.impl;

import io.ebean.DB;
import io.ebean.annotation.Transactional;
import net.caidingke.api.ProductService;
import net.caidingke.domain.Product;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author bowen
 */
@Service(version = "1.0.0")
public class ProductServiceImpl implements ProductService {

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Product findById(Long id) {
        return Product.find.byId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Product product) {
        product.save();
    }
}

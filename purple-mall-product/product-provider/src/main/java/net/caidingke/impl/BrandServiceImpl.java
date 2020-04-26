package net.caidingke.impl;

import com.google.common.base.Strings;
import io.ebean.DB;
import io.ebean.FetchGroup;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

import net.caidingke.api.BrandService;
import net.caidingke.cache.RDSCache;
import net.caidingke.common.mapper.BeanUtils;
import net.caidingke.common.result.ResultPage;
import net.caidingke.domain.Brand;
import net.caidingke.domain.query.QBrand;
import net.caidingke.utils.ResultUtils;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author bowen
 */
@Service(version = "1.0.0")
public class BrandServiceImpl implements BrandService {

    @Override
    public ResultPage<Brand> findBrand(String name, Integer status, int page, int pageSize) {
        QBrand query = Brand.find.where();
        if (status != null) {
            query.status.eq(status);
        }
        if (!Strings.isNullOrEmpty(name)) {
            query.name.eq(name);
        }
        PagedList<Brand> pagedList = query.setFirstRow(page * pageSize).setMaxRows(pageSize).findPagedList();
        return ResultUtils
                .ok(pagedList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBrand(@Nonnull Brand brand) {
        brand.insert();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Long id, @Nonnull Brand brand) {
        Brand dbBrand = Brand.find.byId(id);
        BeanUtils.copyProperties(brand, dbBrand);
        dbBrand.update();
    }

    @Override
    public Brand findById(@Nonnull Long id) {
        System.out.println("ccc");
        Brand bran = Brand.find.byId(id);
        BeanUtils.copyProperties(bran, new Dto());
        return RDSCache.get(Brand.class, id, Brand.find::byId);
    }

    public static class Dto {
        private String name;
        private Long idd;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getIdd() {
            return idd;
        }

        public void setIdd(Long idd) {
            this.idd = idd;
        }

        public static void find() {
        }
    }
}

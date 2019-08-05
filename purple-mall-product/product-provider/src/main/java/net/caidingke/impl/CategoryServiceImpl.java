package net.caidingke.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CreateCache;
import com.google.common.base.Strings;
import io.ebean.DB;
import io.ebean.annotation.Transactional;
import net.caidingke.api.CategoryService;
import net.caidingke.cache.RDSCache;
import net.caidingke.common.mapper.BeanUtils;
import net.caidingke.common.result.ResultPage;
import net.caidingke.domain.Category;
import net.caidingke.domain.query.QCategory;
import net.caidingke.exception.BizException;
import net.caidingke.exception.ErrorCode;
import net.caidingke.utils.ResultUtils;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

/**
 * @author bowen
 */
@Service(version = "1.0.0")
public class CategoryServiceImpl implements CategoryService {

    @CreateCache(name = "category:")
    private Cache cache;

    @Override
    public ResultPage<Category> findCategories(String name, Integer status, int page, int pageSize) {
        QCategory query = Category.find.where();
        if (status != null) {
            query.status.eq(status);
        }
        if (!Strings.isNullOrEmpty(name)) {
            query.name.eq(name);
        }
        return ResultUtils.ok(query.setFirstRow(page * pageSize).setMaxRows(pageSize).findPagedList());
    }

    @Override
    public Category findById(Long id) {
        return RDSCache.get(Category.class, id, Category.find::byId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCategory(@Nonnull Category category) {
        checkCategoryNameDuplication(null, category.getParentId(), category.getName());
        category.insert();
    }

    @Override
    @CacheInvalidate(name = "category:")
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(@Nonnull Category category) {
        Category dbCategory = Category.find.byId(category.getId());
        checkCategoryNameDuplication(category.getId(), category.getParentId(), category.getName());
        if (category.getParentId() != null) {
            checkCategoryCirculation(category.getId(), category.getParentId());
        }
        BeanUtils.copyProperties(category,dbCategory);
        cache.remove(category.getParentId());
        dbCategory.update();
    }

    @Override
    @Transactional(readOnly = true)
    @Cached(name = "category:", cacheType = CacheType.BOTH)
    public List<Category> getTopCategories() {
        return Category.find.where().parentId.isNull().findList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cached(name = "category:", key = "#parentId", cacheType = CacheType.BOTH)
    public List<Category> getChildrenCategoriesByParentId(@Nonnull Long parentId) {
        return Category.find.where().parentId.eq(parentId).findList();
    }

    private void checkCategoryNameDuplication(Long categoryId, Long parentId, String name) {
        List<Category> childrenCategories;
        if (parentId != null) {
            childrenCategories = getChildrenCategoriesByParentId(parentId);
        } else {
            childrenCategories = getTopCategories();
        }

        for (Category childCategory : childrenCategories) {
            if (childCategory.getName().equals(name) && !Objects.equals(childCategory.getId(), categoryId)) {
                throw new BizException(ErrorCode._30001);
            }
        }
    }

    /**
     * category -> parent must not have circle
     */
    private void checkCategoryCirculation(Long id, Long parentId) {
        do {
            if (Objects.equals(id, parentId)) {
                throw new BizException(ErrorCode._30002);
            }
            Category category = findById(parentId);
            parentId = category.getParentId();
        } while (parentId != null);

    }

}

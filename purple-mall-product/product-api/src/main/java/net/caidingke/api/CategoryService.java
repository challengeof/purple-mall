package net.caidingke.api;

import net.caidingke.common.result.ResultPage;
import net.caidingke.domain.Category;

import java.util.List;

/**
 * @author bowen
 */
public interface CategoryService {

    /**
     * 分页查询品牌信息
     *
     * @param name     品牌名称
     * @param status   状态
     * @param page     页码
     * @param pageSize 数量
     * @return 品牌分页数据
     */
    ResultPage<Category> findCategories(String name, Integer status, int page, int pageSize);

    /**
     * 根据id查询分类
     *
     * @param id 分类id
     * @return 分类
     */
    Category findById(Long id);

    /**
     * 创建分类
     *
     * @param category 分类
     */
    void createCategory(Category category);

    /**
     * 更新分类
     *
     * @param category 分类
     */
    void updateCategory(Category category);

    /**
     * 根据父id查找子分类
     *
     * @param parentId 父id
     * @return 当前父下的所有子分类
     */
    List<Category> getChildrenCategoriesByParentId(Long parentId);

    /**
     * 所有的顶级分类
     *
     * @return 顶级分类
     */
    List<Category> getTopCategories();

}

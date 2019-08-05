package net.caidingke.api;

import net.caidingke.common.result.ResultPage;
import net.caidingke.domain.Brand;

/**
 * @author bowen
 */
public interface BrandService {

    /**
     * 分页查询品牌信息
     *
     * @param name     品牌名称
     * @param status   状态
     * @param page     页码
     * @param pageSize 数量
     * @return 品牌分页数据
     */
    ResultPage<Brand> findBrand(String name, Integer status,
                                int page, int pageSize);

    /**
     * 新建品牌
     *
     * @param brand 品牌实体
     */
    void createBrand(Brand brand);

    /**
     * 更新品牌信息
     *
     * @param id    品牌id
     * @param brand 品牌`
     */
    void updateBrand(Long id, Brand brand);

    /**
     * 根据id查询品牌信息
     *
     * @param id 品牌id
     * @return result
     */
    Brand findById(Long id);

}

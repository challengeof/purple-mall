package net.caidingke.business.controller;

import net.caidingke.api.CategoryService;
import net.caidingke.base.BasicController;
import net.caidingke.common.result.Result;
import net.caidingke.domain.Category;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author bowen
 */
@RestController
public class CategoryController extends BasicController {

    @Reference(version = "1.0.0")
    private CategoryService categoryService;

    @PostMapping("/category")
    public Result create(Category category) {
        categoryService.createCategory(category);
        return ok();
    }

    @PutMapping("/category")
    public Result update(Category category) {
        categoryService.updateCategory(category);
        return ok();
    }

    @GetMapping("/category")
    public Result<Category> findById(Long id) {
        return ok(categoryService.findById(id));
    }

    @GetMapping("/category/top")
    public Result<List<Category>> getTopCategories() {
        return ok(categoryService.getTopCategories());
    }

    @GetMapping("/category/children")
    public Result<List<Category>> getChildrenCategoriesByParentId(Long id) {
        return ok(categoryService.getChildrenCategoriesByParentId(id));
    }

}

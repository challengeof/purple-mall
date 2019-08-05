package net.caidingke.domain;

import io.ebean.annotation.Index;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.CategoryBrandXrefFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
public class CategoryBrandXref extends BaseModel {

    public static final CategoryBrandXrefFinder find = new CategoryBrandXrefFinder();

    private static final long serialVersionUID = 4152181941935970369L;
    /**
     * 分类id
     */
    @Index
    private Long categoryId;
    /**
     * 品牌id
     */
    private Long brandId;
}

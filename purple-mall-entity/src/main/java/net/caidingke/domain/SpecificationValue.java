package net.caidingke.domain;

import io.ebean.annotation.Index;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.SpecificationValueFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
@Index(columnNames = {"status", "spec_key_id"})
public class SpecificationValue extends BaseModel {

    public static final SpecificationValueFinder find = new SpecificationValueFinder();

    private static final long serialVersionUID = 4164562657876454105L;
    /**
     * 属性名id
     */
    private Long specKeyId;
    /**
     * 属性值
     */
    private String specValue;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序值
     */
    private Integer sort;
}

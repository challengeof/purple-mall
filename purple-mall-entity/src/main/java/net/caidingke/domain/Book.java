package net.caidingke.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.BookFinder;

import javax.persistence.Entity;

/**
 * @author bowen
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseModel {

    public static final BookFinder find = new BookFinder();
    private static final long serialVersionUID = -7643994087997267032L;
    /**
     * 名字
     */
    private String name;
    /**
     * 状态
     */
    private int status;
}





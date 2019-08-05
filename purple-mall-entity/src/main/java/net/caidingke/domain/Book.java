package net.caidingke.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.BookFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
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

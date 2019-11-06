package net.caidingke.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.BookFinder;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void main(String[] args) {
        String s = "1";
        Long a = 1L + Long.valueOf(s);
        System.out.println(s.getClass());
    }
}





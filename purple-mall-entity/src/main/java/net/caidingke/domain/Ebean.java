package net.caidingke.domain;

import net.caidingke.domain.finder.EbeanFinder;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Version;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
public class Ebean extends BaseModel {

  public static final EbeanFinder find = new EbeanFinder();

    @Version
    private Long version;

    private String name;
}

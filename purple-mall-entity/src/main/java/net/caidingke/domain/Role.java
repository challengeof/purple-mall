package net.caidingke.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.RoleFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
public class Role extends BaseModel {

    public static final RoleFinder find = new RoleFinder();

    private static final long serialVersionUID = 440298941930215684L;

    private String name;

    private String displayName;
}

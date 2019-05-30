package net.caidingke.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.PermissionFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
public class Permission extends BaseModel {

    public static final PermissionFinder find = new PermissionFinder();

    private static final long serialVersionUID = 2726305098180580139L;

    private String name;

    private String displayName;
}

package net.caidingke.domain;

import io.ebean.annotation.Index;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.UserRoleFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
@Index(columnNames = {"user_id", "role_id"})
public class UserRole extends BaseModel {

    public static final UserRoleFinder find = new UserRoleFinder();
    private static final long serialVersionUID = 2860222621618780673L;
    private Long userId;
    private Long roleId;
}

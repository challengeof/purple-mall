package net.caidingke.domain.vo;

import lombok.Data;
import net.caidingke.domain.Role;

/**
 * @author bowen
 */
@Data
public class RoleVo {

    private Long id;
    private String name;
    private String displayName;

    public RoleVo(Role role) {

    }
}

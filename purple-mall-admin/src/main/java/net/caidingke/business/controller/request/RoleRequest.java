package net.caidingke.business.controller.request;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Data;

/**
 * @author bowen
 */
@Data
public class RoleRequest {

    private String name;

    private String displayName;

    private List<Long> permissionIds = Lists.newArrayList();
}

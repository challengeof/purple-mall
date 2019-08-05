package net.caidingke.business.controller.request;

import lombok.Data;

/**
 * @author bowen.
 */
@Data
public class PermissionRequest {

    private String name;
    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 状态 {@link net.caidingke.domain.enums.DisableEnabledStatus}
     */
    private Integer status;

    /**
     * 具体权限
     */
    private String permission;

    /**
     * 显示顺序
     */
    private Integer displayOrder;

}

package net.caidingke.business.controller.request;

import lombok.Data;

/**
 * @author bowen
 */
@Data
public class UserRequest {

    private String username;

    private String realName;

    private String password;

    private boolean enabled;

    private String telephone;
}

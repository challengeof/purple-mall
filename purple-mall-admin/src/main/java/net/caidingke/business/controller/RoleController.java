package net.caidingke.business.controller;

import java.util.List;
import net.caidingke.base.BasicController;
import net.caidingke.business.controller.request.RoleRequest;
import net.caidingke.business.exception.NotFoundException;
import net.caidingke.business.service.RoleService;
import net.caidingke.common.result.Result;
import net.caidingke.domain.Role;
import net.caidingke.domain.User;
import net.caidingke.profile.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bowen
 */
@RestController
public class RoleController extends BasicController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public Result<List<Role>> adminRoles(@CurrentUser User user) {
        return ok(roleService.getRoles());
    }

    @PostMapping("/role")
    public Result create(RoleRequest roleRequst) {
        roleService.create(roleRequst);
        return ok();
    }

    @GetMapping("/role/{id}")
    public Result<Role> role(@CurrentUser User user, @PathVariable Long id) {
        return ok(roleService.findById(id).orElseThrow(NotFoundException::new));
    }

    @PutMapping("/role/{id}")
    public Result modifyRole(@PathVariable Long id, String displayName, String name) {
        roleService.updateRole(id, displayName, name);
        return ok();
    }

    @PutMapping("/role/permission/{id}")
    public Result modifyRolePermissions(@PathVariable("id") Long roleId,
            @RequestParam(value = "permissions[]", required = false) List<Long> permissions) {
        roleService.updateRolePermission(roleId, permissions);
        return ok();
    }

}

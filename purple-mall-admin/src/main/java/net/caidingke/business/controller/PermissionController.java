package net.caidingke.business.controller;

import java.util.List;
import net.caidingke.base.BasicController;
import net.caidingke.business.controller.request.PermissionRequest;
import net.caidingke.business.exception.NotFoundException;
import net.caidingke.business.service.PermissionService;
import net.caidingke.common.result.Result;
import net.caidingke.domain.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bowen
 */
@RestController
public class PermissionController extends BasicController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/permissions")
    public Result<List<Permission>> permissions() {
        return ok(permissionService.findPermissions());
    }

    @PostMapping("/permission")
    public Result permission(PermissionRequest request) {
        permissionService.permission(request);
        return ok();
    }

    @GetMapping("/permission/{id}")
    public Result<Permission> getUserPermission(@PathVariable Long id) {
        return ok(permissionService.findPermissionById(id).orElseThrow(NotFoundException::new));
    }

    @PutMapping("/permission/{id}")
    public Result modifyPermission(
            @PathVariable Long id, PermissionRequest request) {
        permissionService.modifyPermission(id, request);
        return ok();
    }
}

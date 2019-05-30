package net.caidingke.business.service;

import com.google.common.collect.Lists;
import io.ebean.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.caidingke.business.controller.request.PermissionRequest;
import net.caidingke.business.exception.BusinessException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.common.mapper.BeanUtils;
import net.caidingke.domain.Permission;
import net.caidingke.domain.RolePermission;
import net.caidingke.domain.UserRole;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author bowen
 */
@Service
public class PermissionService {

    @Transactional(rollbackFor = Exception.class)
    public void permission(PermissionRequest request) {
        Permission permission = BeanUtils.convert(request, Permission.class);
        permission.insert();
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Permission> findPermissions() {
        return Permission.find.all();
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Optional<Permission> findPermissionById(Long id) {
        return Optional.ofNullable(Permission.find.byId(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modifyPermission(Long id, PermissionRequest request) {
        Permission permission = Permission.find.byId(id);
        if (permission == null) {
            throw new BusinessException(ErrorCode._10004);
        }
        BeanUtils.copyProperties(request, permission);
        permission.update();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean hasPermission(Long userId, String permission) {
        List<Permission> permissions = Lists.newArrayList();
        List<UserRole> userRoles = UserRole.find.where().userId.eq(userId).findList();
        List<RolePermission> rolePermissions = RolePermission.find.where().roleId.in(
                userRoles.stream().map(UserRole::getRoleId).toArray(Long[]::new)).findList();
        List<Long> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            permissions = Permission.find.where().setIdIn(permissionIds.toArray()).findList();
        }
        return permissions.stream().anyMatch(userPermission -> Objects
                .equals(userPermission.getName(), permission));
    }
}

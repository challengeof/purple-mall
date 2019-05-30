package net.caidingke.business.service;

import com.google.common.collect.Lists;
import io.ebean.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.caidingke.business.controller.request.RoleRequest;
import net.caidingke.common.mapper.BeanUtils;
import net.caidingke.domain.Role;
import net.caidingke.domain.RolePermission;
import net.caidingke.domain.UserRole;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author bowen
 */
@Service
public class RoleService {

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Role> getRoles() {
        return Role.find.all();
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Optional<Role> findById(Long id) {
        return Optional.ofNullable(Role.find.byId(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(RoleRequest request) {
        Role role = BeanUtils.convert(request, Role.class);
        role.insert();
        if (CollectionUtils.isNotEmpty(request.getPermissionIds())) {
            for (Long permissionId : request.getPermissionIds()) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(permissionId);
                rolePermission.insert();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRolePermission(Long roleId, List<Long> permissions) {
        RolePermission.find.where().roleId.eq(roleId).delete();
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (Long id : permissions) {
                RolePermission permission = new RolePermission();
                permission.setRoleId(roleId);
                permission.setPermissionId(id);
                permission.insert();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Role> findUserRolesByUserId(Long userId) {
        List<UserRole> userRoles = UserRole.find.where().userId.eq(userId).findList();
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(roleIds)) {
            return Role.find.where().setIdIn(roleIds.toArray()).findList();
        }
        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean hasRole(Long userId, String roleName) {
        List<Role> roles = Lists.newArrayList();
        List<UserRole> userRoles = UserRole.find.where().userId.eq(userId).findList();
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(roleIds)) {

            roles = Role.find.where().setIdIn(roleIds.toArray()).findList();
        }
        return roles.stream().anyMatch(role -> Objects.equals(role.getName(), roleName));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Long id, String displayName, String name) {
        Role role = Role.find.byId(id);
        role.setDisplayName(displayName);
        role.setName(name);
        role.update();
    }
}

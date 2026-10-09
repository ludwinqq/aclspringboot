package bo.academia.carvic.infrastructure.persistence.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserPermissionRule;
import bo.academia.carvic.infrastructure.persistence.permission.PermissionEntity;
import bo.academia.carvic.infrastructure.persistence.role.RoleEntity;

@Component("infraUserMapper")
public class UserMapper {

    public User toDomain(UserEntity entity) {
        if ( entity == null ) return null;
        List<UserPermissionRule> domainRules = entity.getPermissions().stream()
                .map(p -> {
                    PermissionEntity pEntity = p.getPermission();
                    Permission permission = new Permission(
                            pEntity.getId(), pEntity.getName(), pEntity.getTitle(),
                            pEntity.getDescription(), pEntity.getModule(),
                            entity.getStatus(), entity.getCreatedAt(), entity.getUpdatedAt()
                    );
                    return new UserPermissionRule(permission, p.getPermitted());
                })
                .collect(Collectors.toList());
        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setRoleId(entity.getRole().getId());
        user.setStatus(entity.getStatus());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        user.setPermissionRules(domainRules);
        return user;
    }

    public UserEntity toEntity (User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        // IMPORTANTE: Solo mapeamos el ID si ya existe (Actualización). Si es nuevo, va nulo para activar el INSERT automático de Spring Data
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setRefreshTokenHash(domain.getRefreshTokenHash());
        entity.setRequirePasswordChange(domain.getRequirePasswordChange());
        entity.setStatus(domain.getStatus());

        RoleEntity roleRef = new RoleEntity();
        roleRef.setId(domain.getRoleId());
        entity.setRole(roleRef);

        List<UserPermissionEntity> conversionList = domain.getPermissionRules().stream()
                .map(rule -> {
                    PermissionEntity pEntity = new PermissionEntity();
                    pEntity.setId(rule.getPermission().getId());
                    return new UserPermissionEntity(entity, pEntity, rule.isPermitted());
                })
                .collect(Collectors.toList());

        entity.syncPermissions(conversionList);
        return entity;
    }
}

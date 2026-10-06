package bo.academia.carvic.infrastructure.persistence.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RolePermissionRule;
import bo.academia.carvic.infrastructure.persistence.permission.PermissionEntity;

@Component ("infraRoleMapper")
public class RoleMapper {

    //1 Convertir JPA Entity (BD) a Dominio (Negocio)
    public Role toDomain(RoleEntity entity) {
        
        if ( entity == null ) return null;

        //Mapeamos la lista de la tabla intermedia a reglas de dominio
        List<RolePermissionRule> domainRules = entity.getPermissions().stream()
            .map(itemEntity -> {
                PermissionEntity pEntity = itemEntity.getPermission();
                Permission permission = new Permission(
                    pEntity.getId(),
                    pEntity.getName(),
                    pEntity.getTitle(),
                    pEntity.getDescription(),
                    pEntity.getModule(),
                    entity.getStatus(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
                );
                return new RolePermissionRule(permission, itemEntity.getPermitted());
            })
            .collect(Collectors.toList());
        return new Role(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            domainRules
        );
    }

    //2. Convertir de dominio (Negocio) a JPA Entity (BD)
    public RoleEntity toEntity(Role domain) {
        if (domain == null) return null;

        RoleEntity entity = new RoleEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription()
        );
        // Seteamos datos de auditoría si es necesario
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        // Transformamos las reglas del dominio a entidades de la tabla intermedia
        List<RolePermissionEntity> permissionEntities = domain.getPermissionRules().stream()
                .map(rule -> {
                    PermissionEntity pEntity = new PermissionEntity();
                    pEntity.setId(rule.getPermission().getId()); // Solo necesitamos el ID para que JPA lo vincule
                    
                    return new RolePermissionEntity(entity, pEntity, rule.isPermitted());
                })
                .collect(Collectors.toList());

        // Usamos el helper method que creamos en RoleEntity para limpiar y asignar limpiamente
        entity.updatePermissions(permissionEntities);

        return entity;
    }
}

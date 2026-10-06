package bo.academia.carvic.application.role;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bo.academia.carvic.application.role.dto.RolePermissionRequest;
import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;
import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RolePermissionRule;
import bo.academia.carvic.domain.role.RoleRepository;

@Service
public class UpdateRoleUseCase {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository; // Necesario para validar que los permisos existan

    public UpdateRoleUseCase(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role execute(UUID id, String name, String description, List<RolePermissionRequest> permissionRequests) {
        // 1. Buscamos el rol existente
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el rol"));

        // 2. Actualizamos los detalles básicos usando el método del dominio
        existingRole.updateDetails(name, description);

        // 3. Si el frontend envió permisos, transformamos los DTOs a reglas de dominio reales
        if (permissionRequests != null) {
            List<RolePermissionRule> updatedRules = permissionRequests.stream()
                .map(req -> {
                    // Buscamos cada permiso en la BD para asegurar la integridad de datos
                    Permission permission = permissionRepository.findById(req.getPermissionId())
                        .orElseThrow(() -> new IllegalArgumentException("No existe el permiso con ID: " + req.getPermissionId()));
                    
                    // Creamos la regla de dominio
                    return new RolePermissionRule(permission, req.getPermitted());
                })
                .collect(Collectors.toList());

            // Asignamos las nuevas reglas de ACL al rol utilizando el método de dominio
            existingRole.updatePermission(updatedRules);
        }

        // 4. Persistimos los cambios en la infraestructura
        return roleRepository.update(existingRole);
    }
}
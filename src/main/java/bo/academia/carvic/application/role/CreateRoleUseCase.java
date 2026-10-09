package bo.academia.carvic.application.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bo.academia.carvic.application.role.dto.RolePermissionRequest;
import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;
import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RolePermissionRule;
import bo.academia.carvic.domain.role.RoleRepository;

@Service
public class CreateRoleUseCase {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public CreateRoleUseCase(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role execute(String name, String description, List<RolePermissionRequest> permissionRequests) {
        // Creamos la instancia de dominio base
        Role newRole = new Role();
        newRole.setName(name);
        newRole.setDescription(description);
        //(null, name, description, 1, null, null, null);

        // Si se envían permisos en la creación, los procesamos de la misma manera
        if (permissionRequests != null) {
            List<RolePermissionRule> initialRules = permissionRequests.stream()
                .map(req -> {
                    Permission permission = permissionRepository.findById(req.getPermissionId())
                        .orElseThrow(() -> new IllegalArgumentException("No existe el permiso con ID: " + req.getPermissionId()));
                    return new RolePermissionRule(permission, req.getPermitted());
                })
                .collect(Collectors.toList());
                
            newRole.updatePermission(initialRules);
        }

        return roleRepository.save(newRole);
    }
}
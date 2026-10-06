package bo.academia.carvic.application.permission;

import java.util.UUID;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;

@Service 
public class UpdatePermissionUseCase {

    private final PermissionRepository repository;

    public UpdatePermissionUseCase(PermissionRepository repository) {
        this.repository = repository;
    }

    public Permission execute(UUID id, Permission permission) {
        Permission existingPermission = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe permission"));
        existingPermission.setName(permission.getName());
        existingPermission.setTitle(permission.getTitle());
        existingPermission.setDescription(permission.getDescription());
        existingPermission.setModule(permission.getModule());
        return repository.update(existingPermission);
    }
}

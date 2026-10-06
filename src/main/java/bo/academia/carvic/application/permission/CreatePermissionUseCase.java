package bo.academia.carvic.application.permission;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;

@Service
public class CreatePermissionUseCase {
    private final PermissionRepository repository;

    public CreatePermissionUseCase(PermissionRepository repository) {
        this.repository = repository;
    }

    public Permission execute(Permission permission) {
        return repository.save(permission);
    }
}

package bo.academia.carvic.application.permission;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;

@Service 
public class FindByNamePermissionUseCase {

    private final PermissionRepository repository;

    public FindByNamePermissionUseCase(PermissionRepository repository) {
        this.repository = repository;
    }

    public Permission execute(String name) {
        return repository.findByName(name).orElseThrow(() -> new IllegalArgumentException("No existe permiso"));
    }
}

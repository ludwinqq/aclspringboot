package bo.academia.carvic.application.permission;

import java.util.UUID;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;

@Service 
public class FindByIdPermissionUseCase {

    private final PermissionRepository repository;

    public FindByIdPermissionUseCase(PermissionRepository repository) {
        this.repository = repository;
    }

    public Permission execute(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe permiso"));
    }
}

package bo.academia.carvic.application.permission;

import java.util.List;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;

@Service 
public class FindAllPermissionUseCase {

    private final PermissionRepository repository;

    public FindAllPermissionUseCase(PermissionRepository repository) {
        this.repository = repository;
    }

    public List<Permission> execute() {
        return repository.findAll();
    }
}

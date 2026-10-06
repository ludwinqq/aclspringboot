package bo.academia.carvic.application.role;

import java.util.UUID;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;

@Service 
public class FindByIdRoleUseCase {

    private final RoleRepository repository;

    public FindByIdRoleUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    public Role execute(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe este rol"));
    }
}

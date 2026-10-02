package bo.academia.carvic.application.role;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;

@Service 
public class FindByNameRoleUseCase {

    private final RoleRepository repository;

    public FindByNameRoleUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    public Role execute(String name) {

        return repository.findByName(name).orElseThrow(() -> new IllegalArgumentException("No existe este nombre"));
    }
}

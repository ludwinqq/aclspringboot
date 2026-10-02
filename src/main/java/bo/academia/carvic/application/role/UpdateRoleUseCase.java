package bo.academia.carvic.application.role;

import java.util.UUID;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;

@Service 
public class UpdateRoleUseCase {

    private final RoleRepository repository;

    public UpdateRoleUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    public Role execute(Role roleInput) {
        Role existingRole = repository.findById(roleInput.getId()).orElseThrow(() -> new IllegalArgumentException("El rol Id " + roleInput.getId() + " no existe"));
        existingRole.setName(roleInput.getName());
        existingRole.setDescription(roleInput.getDescription());
        return repository.update(existingRole);
    }
}

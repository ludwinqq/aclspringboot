package bo.academia.carvic.application.role;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;

public class CreateRoleUseCase {

    private final RoleRepository roleRepository;

    public CreateRoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role execute(String name, String description) {
        if (roleRepository.findByName(name).isPresent() ) {
            throw new IllegalArgumentException("Role with name " + name + " already exists.");
        }

        Role role = new Role();
        role.setName(name);
        role.setDescription(description);

        return roleRepository.save(role);
    }
}

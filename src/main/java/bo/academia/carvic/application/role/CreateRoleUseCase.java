package bo.academia.carvic.application.role;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;

@Service 
public class CreateRoleUseCase {

    private final RoleRepository roleRepository;

    public CreateRoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role execute(Role roleInput) {
        
        if (roleRepository.findByName(roleInput.getName()).isPresent() ) {
            throw new IllegalArgumentException("Role with name " + roleInput.getName() + " already exists.");
        }

        Role role = new Role();
        role.setName(roleInput.getName());
        role.setDescription(roleInput.getDescription());

        return roleRepository.save(role);
    }

}

package bo.academia.carvic.application.role;

import java.util.List;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;

@Service 
public class FindAllRoleUseCase {

    private final RoleRepository repository;

    public FindAllRoleUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> execute() {
        return repository.findAll();
    }
}

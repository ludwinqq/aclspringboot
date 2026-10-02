package bo.academia.carvic.domain.role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {

    Role save(Role role);
    Optional<Role> findByName(String name);
    Optional<Role> findById(UUID id);
    List<Role> findAll();
    Role update(Role role);
}
